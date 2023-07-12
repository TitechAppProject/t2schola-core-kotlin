package app.titech.t2scholaCore.request

import app.titech.t2scholaCore.request.`interface`.RestAPIRequest
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AssignmentsRequest(wsToken: String) : RestAPIRequest<AssignmentsResponse> {
    override val httpMethod: String = "GET"
    override val queryParameters: Map<String, Any>? = mapOf(
        "moodlewsrestformat" to "json",
        "wstoken" to wsToken,
        "wsfunction" to "mod_assign_get_assignments"
    )

    override fun decode(string: String): AssignmentsResponse {
        val decoder = Json {
            ignoreUnknownKeys = true
//            explicitNulls = true
        }
        return decoder.decodeFromString(string)
    }
}

@Serializable
data class AssignmentsResponse(
    val courses: List<AssignmentCourseResponse>
)

@Serializable
data class AssignmentCourseResponse(
    val id: Int, // Course id.
    val fullname: String, // Course full name.
    val shortname: String, // Course short name.
    val timemodified: Int, // Last time modified.
    val assignments: List<AssignmentResponse> // Assignment info.
)

@Serializable
data class AssignmentResponse(
    val id: Int, // Assignment id.
    val cmid: Int, // Course module id.
    val course: Int, // Course id.
    val name: String, // Assignment name.
    val nosubmissions: Int, // No submissions.
    val submissiondrafts: Int, // Submissions drafts.
    val sendnotifications: Int, // Send notifications.
    val sendlatenotifications: Int, // Send notifications.
    val sendstudentnotifications: Int, // Send student notifications (default).
    val duedate: Int, // Assignment due date.
    val allowsubmissionsfromdate: Int, // Allow submissions from date.
    val grade: Int, // Grade type.
    val timemodified: Int, // Last time assignment was modified.
    val completionsubmit: Int, // If enabled, set activity as complete following submission.
    val cutoffdate: Int, // Date after which submission is not accepted without an extension.
    val gradingduedate: Int? = null, // @since 3.3. The expected date for marking the submissions.
    val teamsubmission: Int, // If enabled, students submit as a team.
    val requireallteammemberssubmit: Int, // If enabled, all team members must submit.
    val teamsubmissiongroupingid: Int, // The grouping id for the team submission groups.
    val blindmarking: Int, // If enabled, hide identities until reveal identities actioned.
    val hidegrader: Int? = null, // @since 3.7. If enabled, hide grader to student.
    val revealidentities: Int, // Show identities for a blind marking assignment.
    val attemptreopenmethod: String, // Method used to control opening new attempts.
    val maxattempts: Int, // Maximum number of attempts allowed.
    val markingworkflow: Int, // Enable marking workflow.
    val markingallocation: Int, // Enable marking allocation.
    val requiresubmissionstatement: Int, // Student must accept submission statement.
    val preventsubmissionnotingroup: Int? = null, // @since 3.2. Prevent submission not in group.
    val submissionstatement: String? = null, // @since 3.2. Submission statement formatted.
    val submissionstatementformat: Int? = null, // @since 3.2. Submissionstatement format (1 = HTML, 0 = MOODLE, 2 = PLAIN or 4 = MARKDOWN).
    //   val configs: AddonModAssignConfig[];, // Configuration settings.
    val intro: String? = null, // Assignment intro, not allways returned because it deppends on the activity configuration.
    val introformat: Int? = null, // Intro format (1 = HTML, 0 = MOODLE, 2 = PLAIN or 4 = MARKDOWN).
    val introfiles: List<CoreWSExternalFile>? = null, // @since 3.2.
    val introattachments: List<CoreWSExternalFile>? = null
)

@Serializable
data class CoreWSExternalFile(
    /**
     * File name.
     */
    val filename: String? = null,

    /**
     * File path.
     */
    val filepath: String? = null,

    /**
     * File size.
     */
    val filesize: Int? = null,

    /**
     * Downloadable file url.
     */
    val fileurl: String? = null,

    /**
     * Time modified.
     */
    val timemodified: Int? = null,

    /**
     * File mime type.
     */
    val mimetype: String? = null,

    /**
     * Whether is an external file.
     */
    val isexternalfile: Boolean? = null,

    /**
     * The repository type for external files.
     */
    val repositorytypeval: String? = null
)