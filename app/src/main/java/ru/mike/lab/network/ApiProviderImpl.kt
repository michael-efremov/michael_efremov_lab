package ru.mike.lab.network

class ApiProviderImpl(private val api: Api) : ApiProvider {

    override suspend fun getRandomPost(): ApiResponse = call { api.getRandomPost() }


    override suspend fun getSectionPost(section: String, page: Int): ApiResponse {
        return call { api.getSection(section = section, page = page) }
    }

    private suspend inline fun <T> call(crossinline block: suspend () -> T): ApiResponse {
        return try {
            ApiResponse.Success(block.invoke() ?: Any())
        } catch (error: Exception) {
            ApiResponse.Error(error)
        }
    }


}