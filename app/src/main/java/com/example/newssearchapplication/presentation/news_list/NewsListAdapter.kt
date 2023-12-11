package com.example.newssearchapplication.presentation.news_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newssearchapplication.databinding.NewsItemBinding
import com.example.newssearchapplication.domain.model.Article

class NewsListAdapter(
    val onTextToSpeechButtonPressed: (Int, String) -> Unit,
    val onWebButtonClicked: (Int, String) -> Unit,
): RecyclerView.Adapter<NewsListAdapter.ArticleViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val news = differ.currentList[position]
        holder.bind(news)
    }

    inner class ArticleViewHolder(val binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: Article){
            binding.itemTitle.text = news.title
            binding.itemBtnTextToSpeech.setOnClickListener{
                news.title.let { title -> onTextToSpeechButtonPressed.invoke(adapterPosition, title) }
            }
            binding.itemBtnToWeb.setOnClickListener{
                news.url.let { url -> onWebButtonClicked.invoke(adapterPosition, url) }
            }
        }
    }

}