package com.example.myapplication.callingapi.api

import com.example.myapplication.callingapi.data.TestResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    suspend fun getTest(): Response<TestResponse>
}