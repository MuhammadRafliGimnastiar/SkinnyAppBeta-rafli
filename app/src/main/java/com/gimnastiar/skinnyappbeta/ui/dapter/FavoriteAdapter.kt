package com.gimnastiar.skinnyappbeta.ui.dapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gimnastiar.skinnyappbeta.data.local.Favorite
import com.gimnastiar.skinnyappbeta.databinding.ItemListFavoriteBinding

class FavoriteAdapter(private val list: List<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ViewHolder(val view: ItemListFavoriteBinding) : RecyclerView.ViewHolder(view.root) {
        private val binding = view
        fun bind(data: Favorite) {
            binding.apply {
                imgFavorite.setImageResource(data.image)
                tvTitleFavorite.text = data.titleArticle
                tvShortDescription.text = data.descArticle
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListFavoriteBinding.inflate(
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
        fun onItemClicked(data: Favorite)
    }


}