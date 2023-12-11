package com.example.newssearchapplication.data.remote

import com.example.cryptocurrencyapp.common.Constants.API_KEY
import com.example.newssearchapplication.domain.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query("searchIn")
        searchIn: String = "title",
        @Query("from")
        fromDate: String,
        @Query("to")
        toDate: String,
        @Query("language")
        language: String = "en",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

}