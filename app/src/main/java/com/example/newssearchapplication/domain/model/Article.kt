package com.example.newssearchapplication.domain.model

data class Article(
    val author: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String
)
