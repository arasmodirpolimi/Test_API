package com.example.myapplication.callingapi.api

import com.example.myapplication.callingapi.data.TestResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    fun getTest(): Single<TestResponse>
//    suspend fun getTest(): Single<TestResponse>
}