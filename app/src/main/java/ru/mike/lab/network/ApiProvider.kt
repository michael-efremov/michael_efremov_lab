package ru.mike.lab.network

sealed class ApiResponse {
    class Success(val response: Any) : ApiResponse()
    class Error(val exception: Throwable) : ApiResponse()
}

interface ApiProvider {

    suspend fun getRandomPost(): ApiResponse

    suspend fun getSectionPost(section: String, page: Int): ApiResponse

}