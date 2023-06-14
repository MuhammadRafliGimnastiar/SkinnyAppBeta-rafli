package com.gimnastiar.skinnyappbeta.ui.detailArticle


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.data.remote.model.Article
import com.gimnastiar.skinnyappbeta.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    private lateinit var viewModel: DetailArticleViewModel

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
            if (it.isFavorite) {
                binding.apply {
                    btnFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    btnFavorite.setOnClickListener {
                    btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                        viewModel.setFavorite(false, data.title)
                    }
                }
            } else {
                binding.apply {
                    btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    btnFavorite.setOnClickListener {
                    btnFavorite.setImageResource(R.drawable.baseline_favorite_24)
                        viewModel.setFavorite(true, data.title)
                    }
                }
            }
        }
    }

    companion object{
        const val TAG = "DetailArticleActivity"
    }
}