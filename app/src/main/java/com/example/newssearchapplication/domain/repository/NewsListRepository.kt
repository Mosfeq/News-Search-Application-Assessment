package com.example.newssearchapplication.domain.repository

import com.example.newssearchapplication.data.remote.NewsApi
import javax.inject.Inject

class NewsListRepository @Inject constructor(
    private val api: NewsApi
){

    suspend fun searchNews(searchQuery: String, from: String, to: String) =
        api.searchNews(
            searchQuery = searchQuery,
            fromDate = from,
            toDate = to
        )

}