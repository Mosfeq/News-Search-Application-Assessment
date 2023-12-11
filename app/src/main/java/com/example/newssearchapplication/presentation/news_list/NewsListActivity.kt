package com.example.newssearchapplication.presentation.news_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrencyapp.common.State
import com.example.newssearchapplication.data.remote.NewsApi
import com.example.newssearchapplication.databinding.ActivityNewsListBinding
import com.example.newssearchapplication.domain.repository.NewsListRepository
import com.example.newssearchapplication.presentation.news_list.viewmodel.NewsListViewModel
import com.example.newssearchapplication.presentation.news_list.viewmodel.NewsListViewModelFactory
import com.example.newssearchapplication.presentation.selected_news.SelectedNewsActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class NewsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsListBinding
    private lateinit var viewModel: NewsListViewModel
    private lateinit var textToSpeech: TextToSpeech

    @Inject lateinit var api: NewsApi

    val TAG = "NewsListActivity"

    val newsListAdapter by lazy {
        NewsListAdapter(
            onTextToSpeechButtonPressed = { pos, title ->
                textToSpeech.speak(
                    title,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
            },
            onWebButtonClicked = {pos, url ->
                val intent = Intent(this, SelectedNewsActivity::class.java)
                intent.putExtra("newsUrl", url)
                startActivity(intent)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)

        val newsListRepository = NewsListRepository(api)
        val viewModelFactory = NewsListViewModelFactory(newsListRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsListViewModel::class.java]

        setContentView(binding.root)
        setupRecyclerView()

        textToSpeech = TextToSpeech(this){ status ->
            if (status == TextToSpeech.SUCCESS){
                val language = textToSpeech.setLanguage(Locale.getDefault())
                if (language == TextToSpeech.LANG_MISSING_DATA ||
                    language == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(this, "Language Not Supported", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnFindNews.setOnClickListener {
            if (binding.etSearchNews.text.toString().isNotEmpty()){
                newsListAdapter.differ.submitList(emptyList())
                viewModel.searchNews(binding.etSearchNews.text.toString())
            } else if (binding.etSearchNews.text.toString().isEmpty()){
                Toast.makeText(this, "Type News to Search" ,Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.searchNews.observe(this, Observer { newsState ->
            when(newsState){
                is State.Success -> {
                    hideProgressBar()
                    newsState.data?.let { newsResponse ->
                        if (newsResponse.articles.isNotEmpty()){
                            newsListAdapter.differ.submitList(newsResponse.articles)
                        } else{
                            Toast.makeText(this, "No news published today related to the search!",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is State.Error -> {
                    hideProgressBar()
                    newsState.message?.let { errorMessage ->
                        Toast.makeText(this, errorMessage,Toast.LENGTH_SHORT).show()
                    }
                }
                is State.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun hideProgressBar(){
        binding.fixedTvLoading.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        binding.fixedTvLoading.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(){
        binding.rvNewsList.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(this@NewsListActivity)
        }
    }

}