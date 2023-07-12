package app.titech.t2scholaCore.request.`interface`

import app.titech.t2scholaCore.T2ScholaClient
import java.net.HttpCookie

interface Request<Response> {
    val baseURL: String
    val httpMethod: String
    val path: String
    val queryParameters: Map<String, Any>?
    val headerFields: Map<String, String>?
    val cookies: List<HttpCookie>?

    //    val requestBody: RequestBody
//    fun encode(requestBody: RequestBody): ByteArray
    fun decode(string: String): Response
}

interface T2ScholaRequest<Response> : Request<Response> {
    override val baseURL: String
        get() = "https://${T2ScholaClient.baseHost}"
}

interface RestAPIRequest<Response> : T2ScholaRequest<Response> {
    override val path: String
        get() = "/webservice/rest/server.php"

    override val headerFields: Map<String, String>?
        get() = mapOf(
            "User-Agent" to "Mozilla/5.0 (iPad; CPU OS 13_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 MoodleMobile t2schola",
            "Content-Type" to "application/json"
        )

    override val cookies: List<HttpCookie>?
        get() = null

}