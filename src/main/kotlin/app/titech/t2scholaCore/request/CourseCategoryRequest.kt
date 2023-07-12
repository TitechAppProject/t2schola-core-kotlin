package app.titech.t2scholaCore.request

import app.titech.t2scholaCore.request.`interface`.RestAPIRequest
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CourseCategoryRequest(wsToken: String) : RestAPIRequest<CourseCategoriesResponse> {
    override val httpMethod: String = "GET"
    override val queryParameters: Map<String, Any>? = mapOf(
        "moodlewsrestformat" to "json",
        "wstoken" to wsToken,
        "wsfunction" to "core_course_get_categories"
    )

    override fun decode(string: String): CourseCategoriesResponse {
        val decoder = Json {
            ignoreUnknownKeys = true
        }
        return decoder.decodeFromString(string)
    }
}

typealias CourseCategoriesResponse = List<CourseCategoryResponse>

@Serializable
data class CourseCategoryResponse(
    val id: Int,
    val name: String,
    val parent: Int,
    val depth: Int,
    val path: String
)