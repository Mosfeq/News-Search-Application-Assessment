package com.example.newssearchapplication.presentation.news_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newssearchapplication.domain.repository.NewsListRepository

class NewsListViewModelFactory(
    val newsRepository: NewsListRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsListViewModel(newsRepository) as T
    }
}