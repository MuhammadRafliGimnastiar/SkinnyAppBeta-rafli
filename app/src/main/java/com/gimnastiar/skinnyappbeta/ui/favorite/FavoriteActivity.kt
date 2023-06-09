package com.gimnastiar.skinnyappbeta.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.data.local.Favorite
import com.gimnastiar.skinnyappbeta.data.remote.model.Article
import com.gimnastiar.skinnyappbeta.databinding.ActivityFavoriteBinding
import com.gimnastiar.skinnyappbeta.ui.dapter.FavoriteAdapter
import com.gimnastiar.skinnyappbeta.ui.detailArticle.DetailArticleActivity
import com.gimnastiar.skinnyappbeta.ui.detailArticle.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vmFactory = FavoriteViewModelFactory(this)
        viewModel = ViewModelProvider(
            this,
            vmFactory
        ).get(FavoriteViewModel::class.java)

        viewModel.getFavorite()
        setData()

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun setData() {
        viewModel.favoriteData.observe(this) {
            if(it.size == 0) {
                showRecycler(false)
            } else {
                setAdapter(it)
                showRecycler(true)
            }
        }
    }

    private fun showRecycler(dataFound: Boolean) {
        if (dataFound) {
            binding.apply {
                rvArticle.visibility = View.VISIBLE
                animateNoData.visibility = View.GONE
                tvNoData.visibility = View.GONE
            }
        } else {
            binding.apply {
                rvArticle.visibility = View.GONE
                animateNoData.visibility = View.VISIBLE
                tvNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun setAdapter(data: List<Favorite>) {
        val adapter = FavoriteAdapter(data)
        binding.apply {
            rvArticle.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvArticle.adapter = adapter
        }

        adapter.setOnItemClickCallback (object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Favorite) {
                Toast.makeText(this@FavoriteActivity, data.titleArticle, Toast.LENGTH_SHORT).show()
                val article = Article(
                    data.image,
                    data.titleArticle,
                    data.descArticle,
                    data.descHandleArticle
                )
                val intent = Intent(this@FavoriteActivity, DetailArticleActivity::class.java)
                intent.putExtra(DetailArticleActivity.TAG, article)
                startActivity(intent)
            }
        })


    }

    override fun onResume() {
        super.onResume()

        viewModel.getFavorite()
    }

}