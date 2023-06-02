package com.gimnastiar.skinnyappbeta.ui.dapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gimnastiar.skinnyappbeta.data.remote.model.ImageData
import com.gimnastiar.skinnyappbeta.databinding.ItemBannerSlideBinding

class ImageSliderAdapter(private val listImage: ArrayList<Int>) : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {

    inner class ViewHolder(view: ItemBannerSlideBinding) : RecyclerView.ViewHolder(view.root) {
        private val binding = view
        fun bind(data: Int) {
            binding.imgSlider.setImageResource(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBannerSlideBinding.inflate(
                LayoutInflater.from(
                    parent.context),
                    parent,
                false)
        )
    }

    override fun getItemCount(): Int = listImage.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listImage[position])
    }


}