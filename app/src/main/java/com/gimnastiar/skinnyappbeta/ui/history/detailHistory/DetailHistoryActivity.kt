package com.gimnastiar.skinnyappbeta.ui.history.detailHistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gimnastiar.skinnyappbeta.R
import com.gimnastiar.skinnyappbeta.data.remote.model.DaftarHistoryItem
import com.gimnastiar.skinnyappbeta.databinding.ActivityDetailHistoryBinding
import com.gimnastiar.skinnyappbeta.ui.dapter.HandlingAdapter

class DetailHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<DaftarHistoryItem>(TAG)
        setContent(data)
    }

    private fun setContent(data: DaftarHistoryItem?) {
        if (data != null) {
            binding.apply {
                Glide.with(this@DetailHistoryActivity)
                    .load(data.image.signedUrl)
                    .into(imgInput)

                tvTitleDeases.text = data.penanganan.terdeteksiJenis
                tvDeasesDescription.text = data.penanganan.deskripsi

                rvListHandlingDeases.layoutManager = LinearLayoutManager(this@DetailHistoryActivity)
                val adapter = HandlingAdapter(data.penanganan.judulPenanganan, data.penanganan.penangananYangDapatDilakukan)
                rvListHandlingDeases.adapter = adapter
            }
        }
    }


    companion object{
        const val TAG = "DetailHistoryActivity"
    }
}