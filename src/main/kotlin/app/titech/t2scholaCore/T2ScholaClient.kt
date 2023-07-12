package app.titech.t2scholaCore

import app.titech.t2scholaCore.request.*
import java.net.HttpCookie

class T2ScholaClient {
    private val apiClient = APIClientImpl()

    suspend fun getToken(authCookies: List<HttpCookie>): Result<String> =
        apiClient.send(LoginRequest(authCookies))

    suspend fun getSiteInfo(wsToken: String): Result<SiteInfoResponse> =
        apiClient.send(SiteInfoRequest(wsToken))

    suspend fun getUserCourses(userId: Int, wsToken: String): Result<UserEnrolCoursesResponse> =
        apiClient.send(UserEnrolCourseRequest(userId, wsToken))

    suspend fun getCourseCategories(wsToken: String): Result<CourseCategoriesResponse> =
        apiClient.send(CourseCategoryRequest(wsToken))

    suspend fun getCourseContents(courseId: Int, wsToken: String) =
        apiClient.send(CourseContentsRequest(courseId, wsToken))

    suspend fun getAssignments(wsToken: String): Result<AssignmentsResponse> =
        apiClient.send(AssignmentsRequest(wsToken))

    suspend fun getAssignmentSubmissionStatus(
        assignmentId: Int,
        userId: Int,
        wsToken: String
    ): Result<AssignmentSubmissionStatusResponse> =
        apiClient.send(AssignmentSubmissionStatusRequest(assignmentId, userId, wsToken))

    companion object {
        var baseHost = "t2schola.titech.ac.jp"

        fun changeToMock() {
            baseHost = "t2schola-mock.titech.app"
        }
    }
}


