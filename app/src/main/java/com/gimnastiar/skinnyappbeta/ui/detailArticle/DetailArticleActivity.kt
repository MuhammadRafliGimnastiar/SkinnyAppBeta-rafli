package com.gimnastiar.skinnyappbeta.ui.detailArticle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.data.local.Favorite
import com.gimnastiar.skinnyappbeta.ui.MainActivity
import com.gimnastiar.skinnyappbeta.data.remote.model.Article
import com.gimnastiar.skinnyappbeta.databinding.ActivityDetailArticleBinding
import com.gimnastiar.skinnyappbeta.ui.ViewModelFactory
import kotlin.properties.Delegates

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    private lateinit var viewModel: DetailArticleViewModel

    private var isFavorite: Boolean by Delegates.notNull()
    private var data: Article? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vmFactory = FavoriteViewModelFactory(this)
        viewModel = ViewModelProvider(
            this,
            vmFactory
        ).get(DetailArticleViewModel::class.java)

        data = intent.getParcelableExtra<Article>(TAG)
        setData(data)
    }

    private fun setData(data: Article?){
        if (data != null) {
            with(binding) {
                imgDetail.setImageResource(data.photo)
                tvTitle.text = data.title
                tvDescription.text = data.description
                tvTextHandle.text = data.textHandler
            }

            dataObserve(data)

        }
    }

    private fun dataObserve(data: Article) {
        viewModel.getData(data.title)
        viewModel.valueFavorite.observe(this@DetailArticleActivity) {
            var value = Favorite(
                image = data.photo,
                titleArticle = data.title,
                descArticle = data.description,
                descHandleArticle = data.textHandler,
                isFavorite = false
            )
            if (it == null) {
                checkIsFavorite(value, false)
                Log.i("Test database", "data null")
            } else {
                checkIsFavorite(value, true)
                Log.i("Test database", "data not null")
            }
        }
    }

    private fun addToFavorite(data: Article) {
        var data = Favorite(
            image = data.photo,
            titleArticle = data.description,
            descArticle = data.description,
            descHandleArticle = data.textHandler,
            isFavorite = false
        )
        viewModel.addData(data)
    }
    private fun checkIsFavorite(source: Favorite, isExist: Boolean) {
        if (isExist) {
            viewModel.getFavorite(source.titleArticle)
            viewModel.valueFavorite.observe(this@DetailArticleActivity) {
                if (it.isFavorite) {
                    binding.apply {
                        btnFavorite.setImageResource(R.drawable.baseline_favorite_24)
                        btnFavorite.setOnClickListener {
                            viewModel.setFavorite(false, source.titleArticle)
                            Log.i("Test database", "Remove data in database")
                        }
                    }
                } else {
                    binding.apply {
                        btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                        btnFavorite.setOnClickListener {
                            viewModel.setFavorite(true, source.titleArticle)
//                        viewModel.addData(data)
                            Log.i("Test database", "Success add to Favorite")
                        }
                    }
                }
            }
        } else{
            binding.apply {
                btnFavorite.setOnClickListener {
                    viewModel.addData(source)
//                        viewModel.addData(data)
                    btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    Log.i("Test database", "Success add data to database")
                }
            }
//            dataObserve()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()

    }


    companion object{
        const val TAG = "DetailArticleActivity"
    }
}