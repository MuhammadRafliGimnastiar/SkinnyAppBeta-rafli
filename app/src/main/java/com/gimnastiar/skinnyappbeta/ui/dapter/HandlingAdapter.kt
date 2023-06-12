package com.gimnastiar.skinnyappbeta.ui.dapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gimnastiar.skinnyappbeta.databinding.ItemListHandlingBinding

class HandlingAdapter(private val listTitle: List<String>, private val listHandling: List<String>) : RecyclerView.Adapter<HandlingAdapter.ViewHolder>() {


    inner class ViewHolder(view: ItemListHandlingBinding) : RecyclerView.ViewHolder(view.root) {
        private val binding = view
        fun bind(listTitle: String, listHandling: String) {
            binding.apply {
                tvTitleListDescription.text = listTitle
                tvHandilngDescription.text = listHandling
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListHandlingBinding.inflate(
                LayoutInflater.from(
                    parent.context),
                parent,
                false)
        )
    }

    override fun getItemCount(): Int = listTitle.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listTitle[position], listHandling[position])

    }

}