package app.titech.t2scholaCore.request

import app.titech.t2scholaCore.request.`interface`.T2ScholaRequest
import org.jsoup.Jsoup
import java.net.HttpCookie
import java.util.*
import kotlin.random.Random

class T2ScholaLoginError(message: String) : Exception(message)

class LoginRequest(authCookies: List<HttpCookie>) : T2ScholaRequest<String> {
    override val httpMethod: String = "GET"
    override val path: String = "/admin/tool/mobile/launch.php"
    override val queryParameters: Map<String, Any>? = mapOf(
        "service" to "moodle_mobile_app",
        "passport" to Random.nextDouble(0.0, 1000.0),
        "urlscheme" to "mmt2schola"
    )
    override val headerFields: Map<String, String>? = mapOf(
        "User-Agent" to "Mozilla/5.0 (iPad; CPU OS 13_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1 Mobile/15E148 Safari/604.1",
    )

    override val cookies: List<HttpCookie>? = authCookies

    override fun decode(string: String): String {
        val doc = Jsoup.parse(string)

        val launchapp =
            doc.select("a#launchapp").firstOrNull() ?: throw T2ScholaLoginError("parseToken")
        val href = launchapp.attr("href") ?: throw T2ScholaLoginError("parseToken")
        val decodedToken = href.replace("mmt2schola://token=", "")
        val tokenByte = Base64.getDecoder().decode(decodedToken)
        val token = tokenByte.toString(Charsets.UTF_8)

        val spitedToken = token.splitToSequence(":::").toList()

        return if (spitedToken.count() > 2) {
            spitedToken[1]
        } else {
            ""
        }
    }
}