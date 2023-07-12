package app.titech.t2scholaCore

import app.titech.t2scholaCore.request.`interface`.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.*

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

interface APIClient {
    suspend fun <Response> send(request: Request<Response>): Result<Response>
}

class APIClientImpl : APIClient {
    override suspend fun <Response> send(request: Request<Response>): Result<Response> =
        withContext(Dispatchers.IO) {
            var urlString = request.baseURL + request.path
            request.queryParameters?.run {
                urlString += "?" + this.map { "${it.key}=${it.value}" }.joinToString("&")
            }

            val url = URL(urlString)
            val cookies = request.cookies?.toMutableSet() ?: mutableSetOf()
            var connection =
                generateUrlConnection(url, request.httpMethod, request.headerFields, cookies)

            do {
                try {
                    connection.connect()
                } catch (e: Exception) {
                    return@withContext Result.Error(e)
                }

                connection.headerFields["Set-Cookie"]?.flatMap {
                    HttpCookie.parse(it)
                }?.forEach {
                    if (it.domain == null) {
                        it.domain = connection.url.host
                    }
                    if (cookies.contains(it)) {
                        cookies.remove(it)
                    }
                    cookies.add(it)
                }

                var needRedirect = false
                if (connection.responseCode in 300..399) {
                    val location = connection.headerFields["Location"]!!.first()
                    try {
                        val locationURL = when {
                            location.startsWith("?") -> URL(url.protocol + "://" + url.host + url.path + location)
                            location.startsWith("/") -> URL(url.protocol + "://" + url.host + location)
                            else -> URL(location)
                        }

                        connection =
                            generateUrlConnection(locationURL, "GET", request.headerFields, cookies)
                        needRedirect = true
                    } catch (e: Exception) {
                        needRedirect = false
                    }
                }
            } while (needRedirect)

            val br = BufferedReader(InputStreamReader(connection.inputStream))

            val sb = StringBuilder()

            for (line in br.readLines()) {
                sb.append(line)
            }

            br.close()

            val html = sb.toString()

            return@withContext Result.Success(request.decode(html))
        }

    private fun generateUrlConnection(
        url: URL,
        httpMethod: String,
        headerFields: Map<String, String>?,
        cookies: Set<HttpCookie>
    ): HttpURLConnection {
        val connection = url.openConnection() as HttpURLConnection

        headerFields?.forEach {
            connection.setRequestProperty(it.key, it.value)
        }
        connection.setRequestProperty(
            "Cookie",
            cookies.map { "${it.name}=${it.value}" }.joinToString("; ")
        )
        connection.requestMethod = httpMethod
        connection.instanceFollowRedirects = false

        return connection
    }
}