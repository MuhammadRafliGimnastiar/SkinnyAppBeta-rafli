package com.gimnastiar.skinnyappbeta.ui.dapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gimnastiar.skinnyappbeta.data.remote.model.Article
import com.gimnastiar.skinnyappbeta.data.remote.model.DaftarHistoryItem
import com.gimnastiar.skinnyappbeta.databinding.ItemListHistoryBinding

class HistoryAdapter(private val list: List<DaftarHistoryItem>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(view: ItemListHistoryBinding) : RecyclerView.ViewHolder(view.root) {
        private val binding = view
        fun bind(data: DaftarHistoryItem) {
            binding.apply {
                Glide.with(imgHistory.context)
                    .load(data.image.signedUrl)
                    .into(imgHistory)
                tvTitleDeases.text = data.penanganan.terdeteksiJenis
                tvDescription.text = data.penanganan.deskripsi
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListHistoryBinding.inflate(
                LayoutInflater.from(
                    parent.context),
                parent,
                false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(list[holder.position]) }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DaftarHistoryItem)
    }


}