package com.gimnastiar.skinnyappbeta.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponsePredict(

	@field:SerializedName("Penanganan")
	val penanganan: Penanganan,

	@field:SerializedName("Error")
	val error: Boolean,

	@field:SerializedName("Eessage")
	val message: String,

)

@Parcelize
data class Penanganan(

	@field:SerializedName("Class terprediksi")
	val classTerprediksi: String?,

	@field:SerializedName("Terdeteksi Jenis")
	val terdeteksiJenis: String,

	@field:SerializedName("Judul Penanganan")
	val judulPenanganan: List<String>,

	@field:SerializedName("Deskripsi")
	val deskripsi: String,

	@field:SerializedName("Penanganan yang dapat dilakukan")
	val penangananYangDapatDilakukan: List<String>
) : Parcelable