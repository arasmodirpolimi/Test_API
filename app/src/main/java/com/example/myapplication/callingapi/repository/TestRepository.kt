package com.example.myapplication.callingapi.repository

import com.example.myapplication.callingapi.api.ApiService
import com.example.myapplication.callingapi.data.TestResponse
import javax.inject.Inject

class TestRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getTest(): Result<TestResponse> {
        return try {
            val response = apiService.getTest()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(NullBodyException())
                }
            } else {
                Result.failure(
                    ApiException(
                        "API call failed: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    class ApiException(message: String) : Exception(message)
    class NullBodyException : Exception("Response body is null")
}
