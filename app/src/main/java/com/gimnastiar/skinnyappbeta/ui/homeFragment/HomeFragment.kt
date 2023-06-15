package com.gimnastiar.skinnyappbeta.ui.homeFragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.data.local.Favorite
import com.gimnastiar.skinnyappbeta.data.remote.model.Article
import com.gimnastiar.skinnyappbeta.data.repository.LoginRepository
import com.gimnastiar.skinnyappbeta.data.repository.Resource
import com.gimnastiar.skinnyappbeta.databinding.FragmentHomeBinding
import com.gimnastiar.skinnyappbeta.ui.ViewModelFactory
import com.gimnastiar.skinnyappbeta.ui.dapter.ArticleAdapter
import com.gimnastiar.skinnyappbeta.ui.dapter.ImageSliderAdapter
import com.gimnastiar.skinnyappbeta.ui.detailArticle.DetailArticleActivity
import com.gimnastiar.skinnyappbeta.ui.detailArticle.FavoriteViewModelFactory
import com.gimnastiar.skinnyappbeta.utils.LoginPreference
import java.text.FieldPosition

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var imageViewsList: ArrayList<Int>
    private lateinit var dots: ArrayList<TextView>

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(requireContext())
        ).get(HomeViewModel::class.java)

        imageViewsList = ArrayList()
        imageViewsList.add(R.drawable.banner_tiga)
        imageViewsList.add(R.drawable.banner_dua)
        imageViewsList.add(R.drawable.banner_satu)
        dots = ArrayList()

        setSlideAdapter(imageViewsList)
        setArticleAdapter()
        showView(false)

        Handler(Looper.getMainLooper()).postDelayed({
            showView(true)
        }, 800)

    }

    private fun showView(visible: Boolean) {
        binding.apply {
            viewPager.isVisible = visible
            dotsIndicator.isVisible = visible
            tvArtikel.isVisible = visible
            rvArticle.isVisible = visible
            shimmerViewContainer.isVisible = !visible
        }
    }

    private fun setArticleAdapter() {
        binding.rvArticle.layoutManager = LinearLayoutManager(requireContext())
        val articleAdapter = ArticleAdapter(getListArticle())
        binding.rvArticle.adapter = articleAdapter

        articleAdapter.setOnItemClickCallback(object : ArticleAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Article) {
                val dataDetail = Article(
                    data.photo,
                    data.title,
                    data.description,
                    data.textHandler
                )
                val intent = Intent(requireContext(), DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.TAG, dataDetail)
                startActivity(intent)
//                Toast.makeText(requireContext(), "Artikel ${data.title}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getListArticle(): ArrayList<Article> {
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataTitle = resources.getStringArray(R.array.data_title_article)
        val dataDescription = resources.getStringArray(R.array.data_description_article)
        val dataTextHandel = resources.getStringArray(R.array.data_handle_article)
        val listArticle = ArrayList<Article>()
        for (i in dataTitle.indices) {
            val hero = Article(dataPhoto.getResourceId(i, -1), dataTitle[i], dataDescription[i], dataTextHandel[i] )
            val dataFavorite = Favorite(dataPhoto.getResourceId(i, -1), dataTitle[i], dataDescription[i], dataTextHandel[i], false)
            viewModel.addData(dataFavorite)
            listArticle.add(hero)
        }
        return listArticle
    }

    private fun setSlideAdapter(listImage: ArrayList<Int>) {
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
        val color = ContextCompat.getColor(requireContext(), resources.getIdentifier("green_1", "color", requireActivity().packageName))
        val color1 = ContextCompat.getColor(requireContext(), resources.getIdentifier("grey", "color", requireActivity().packageName))
        for(i in 0 until imageViewsList.size) {
            if(i == position) {
                dots[i].setTextColor(color)
            } else {
                dots[i].setTextColor(color1)
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