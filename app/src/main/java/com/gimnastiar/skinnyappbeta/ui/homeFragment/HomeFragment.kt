package com.gimnastiar.skinnyappbeta.ui.homeFragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.databinding.FragmentHomeBinding
import com.gimnastiar.skinnyappbeta.ui.dapter.ImageSliderAdapter
import java.text.FieldPosition

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageViewsList: ArrayList<Int>
    private lateinit var dots: ArrayList<TextView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageViewsList = ArrayList()
        imageViewsList.add(R.drawable.banner_satu)
        imageViewsList.add(R.drawable.banner_dua)
        dots = ArrayList()

        setAdapter(imageViewsList)

    }

    private fun setAdapter(listImage: ArrayList<Int>) {
        val adapter = ImageSliderAdapter(listImage)
        binding.viewPager.adapter = adapter

        setIndicator()
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                selectedDot(position)
                super.onPageSelected(position)
            }
        })
    }

    private fun selectedDot(position: Int) {
        for(i in 0 until imageViewsList.size) {
            if(i == position) {
                dots[i].setTextColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_primary))
            } else {
                dots[i].setTextColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_secondary))
            }
        }
    }

    private fun setIndicator() {
        for (i in 0 until imageViewsList.size) {
            dots.add(TextView(requireContext()))
            dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()

            dots[i].textSize = 18f
            binding.dotsIndicator.addView(dots[i])
        }
    }


}