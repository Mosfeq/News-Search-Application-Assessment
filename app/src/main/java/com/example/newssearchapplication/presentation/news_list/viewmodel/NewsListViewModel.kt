package com.example.newssearchapplication.presentation.news_list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.common.State
import com.example.newssearchapplication.domain.model.NewsResponse
import com.example.newssearchapplication.domain.repository.NewsListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor (
    val newsRepository: NewsListRepository
): ViewModel(){

    private val calendar = Calendar.getInstance()
    private val currentDay: Int = calendar.get(Calendar.DATE)
    private val currentMonth: Int = calendar.get(Calendar.MONTH) + 1
    private val currentYear: Int = calendar.get(Calendar.YEAR)
    private val currentDate = "$currentYear-$currentMonth-$currentDay"

    val searchNews: MutableLiveData<State<NewsResponse>> = MutableLiveData()

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(State.Loading())
        val newsResponse = newsRepository.searchNews(searchQuery, currentDate, currentDate)
        searchNews.postValue(manageResponse(newsResponse))
    }

    private fun manageResponse(newsListResponse: Response<NewsResponse>): State<NewsResponse>{
        if (newsListResponse.isSuccessful){
            newsListResponse.body()?.let { response ->
                return State.Success(response)
            }
        }
        return State.Error(newsListResponse.message())
    }

}