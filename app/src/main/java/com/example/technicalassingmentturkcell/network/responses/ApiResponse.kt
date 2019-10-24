package com.example.technicalassingmentturkcell.network.responses

import retrofit2.Response

sealed class ApiResponse<T>(
    val data: T? = null,
    val message: String? = null
) {
    fun create(error: Throwable): ApiResponse<T> {
        return ApiErrorResponse(
            error.message ?: "Unknown Error",
            null
        )
    }

    fun create(response: Response<T>): ApiResponse<T> {
        return if (response.isSuccessful) {
            val data: T? = response.body()
            if (data == null || response.code() == 204) {
                ApiEmptyResponse()
            } else {
                ApiSuccessResponse(
                    data
                )
            }
        } else {
            ApiErrorResponse(
                response.errorBody().toString()
            )
        }
    }

    class ApiSuccessResponse<T>(data: T) : ApiResponse<T>(data)
    class ApiEmptyResponse<T> : ApiResponse<T>()
    class ApiErrorResponse<T>(message: String, data: T? = null) : ApiResponse<T>(data, message)
}