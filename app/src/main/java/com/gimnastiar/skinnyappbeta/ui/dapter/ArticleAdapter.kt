package com.gimnastiar.skinnyappbeta.ui.dapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gimnastiar.skinnyappbeta.data.remote.model.Article
import com.gimnastiar.skinnyappbeta.databinding.ItemListArticleBinding

class ArticleAdapter(private val list: ArrayList<Article>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(view: ItemListArticleBinding) : RecyclerView.ViewHolder(view.root) {
        private val binding = view
        fun bind(data: Article) {
            binding.apply {
                imgArticle.setImageResource(data.photo)
                tvTitleArticle.text = data.title
                tvShortDescription.text = data.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListArticleBinding.inflate(
                LayoutInflater.from(
                    parent.context),
                    parent,
                false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(list[holder.adapterPosition]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Article)
    }


}