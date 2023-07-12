package app.titech.t2scholaCore.request

import app.titech.t2scholaCore.request.`interface`.RestAPIRequest
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AssignmentSubmissionStatusRequest(assignmentId: Int, userId: Int, wsToken: String) :
    RestAPIRequest<AssignmentSubmissionStatusResponse> {
    override val httpMethod: String = "GET"
    override val queryParameters: Map<String, Any>? = mapOf(
        "moodlewsrestformat" to "json",
        "wstoken" to wsToken,
        "wsfunction" to "mod_assign_get_submission_status",
        "assignid" to assignmentId,
        "userid" to userId
    )

    override fun decode(string: String): AssignmentSubmissionStatusResponse {
        val decoder = Json {
            ignoreUnknownKeys = true
//            explicitNulls = true
        }
        return decoder.decodeFromString(string)
    }
}

@Serializable
data class AssignmentSubmissionStatusResponse(
    val lastattempt: AssignmentSubmissionLastAttempt? = null, // Last attempt information.
    val feedback: AssignmentSubmissionFeedback? = null, // Feedback for the last attempt.
    val previousattempts: List<AssignmentSubmissionPreviousAttempt>? = null
)

@Serializable
data class AssignmentSubmissionLastAttempt(
    val submission: AssignmentSubmission? = null // Submission info.
)

@Serializable
data class AssignmentSubmissionFeedback(
    val grade: AssignmentSubmissionGrade? = null, // Grade information.
    val gradefordisplay: String? = null, // Grade rendered into a format suitable for display.
    val gradeddate: Int? = null // The date the user was graded.
//    val plugins: [AddonModAssignPlugin]? // Plugins info.
)

@Serializable
data class AssignmentSubmissionGrade(
    val id: Int, // Grade id.
    val assignment: Int? = null, // Assignment id.
    val userid: Int, // Student id.
    val attemptnumber: Int, // Attempt number.
    val timecreated: Int, // Grade creation time.
    val timemodified: Int, // Grade last modified time.
    val grader: Int, // Grader, -1 if grader is hidden.
    val grade: String? = null, // Grade.
    val gradefordisplay: String? = null // Grade rendered into a format suitable for display.
)

@Serializable
data class AssignmentSubmissionPreviousAttempt(
    val attemptnumber: Int, // Attempt number.
    val submission: AssignmentSubmission? = null, // Submission info.
    val grade: AssignmentSubmissionGrade? = null // Grade information.
//    val feedbackplugins: [AddonModAssignPlugin]? // Feedback info.
)

@Serializable
data class AssignmentSubmission(
    val id: Int, // Submission id.
    val userid: Int, // Student id.
    val attemptnumber: Int, // Attempt number.
    val timecreated: Int, // Submission creation time.
    val timemodified: Int, // Submission last modified time.
    val status: String // Submission status.
//    val groupid: Int // Group id.
//    val assignment: Int? // Assignment id.
//    val latest: Int? // Latest attempt.
//    val plugins: [AddonModAssignPlugin]?; // Plugins.
//    val gradingstatus: String? // @since 3.2. Grading status.
)