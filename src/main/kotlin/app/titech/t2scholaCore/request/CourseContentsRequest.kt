package app.titech.t2scholaCore.request

import app.titech.t2scholaCore.request.`interface`.RestAPIRequest
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CourseContentsRequest(courseId: Int, wsToken: String) :
    RestAPIRequest<CourseContentsResponse> {
    override val httpMethod: String = "GET"
    override val queryParameters: Map<String, Any>? = mapOf(
        "moodlewsrestformat" to "json",
        "wstoken" to wsToken,
        "wsfunction" to "core_course_get_contents",
        "courseid" to courseId
    )

    override fun decode(string: String): CourseContentsResponse {
        val decoder = Json {
            ignoreUnknownKeys = true
        }
        return decoder.decodeFromString(string)
    }
}

typealias CourseContentsResponse = List<CourseContentResponse>

@Serializable
data class CourseContentResponse(
    val id: Int,
    val name: String,
    val summary: String,
    val modules: List<CourseContentModule>
)

@Serializable
data class CourseContentModule(
    val id: Int,
    val modname: String,
    val url: String? = null,
    val name: String,
    val description: String? = null,
    val modicon: String,
    val modplural: String,
    val completion: Int,
    val completiondata: CourseContentModuleCompletionData? = null,
    val contents: List<CourseContentModuleContent>? = null
)

@Serializable
data class CourseContentModuleCompletionData(
    val stateval: Int
)

@Serializable
data class CourseContentModuleContent(
    val type: String,
    val filename: String,
    val filepath: String? = null,
    val filesize: Long,
    val fileurl: String? = null,
    val mimetype: String? = null,
    val timecreated: Int? = null,
    val timemodified: Int? = null,
    val sortorder: Int? = null,
    val userid: Int? = null,
    val author: String? = null,
    val licenseval: String? = null
)

