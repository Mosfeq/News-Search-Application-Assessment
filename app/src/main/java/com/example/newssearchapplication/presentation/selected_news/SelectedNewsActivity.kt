package com.example.newssearchapplication.presentation.selected_news

import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.newssearchapplication.databinding.ActivitySelectedNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectedNewsActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySelectedNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedNewsUrl = intent.getStringExtra("newsUrl")
        binding.webView.apply {
            webViewClient = WebViewClient()
            selectedNewsUrl?.let { loadUrl(it) }
        }

    }

}