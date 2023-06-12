package com.gimnastiar.skinnyappbeta.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseUpToDb(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("Error")
	val error: Boolean,

	@field:SerializedName("History")
	val history: History
)

data class History(

	@field:SerializedName("Terdeteksi Jenis")
	val terdeteksiJenis: String,

	@field:SerializedName("Class")
	val mclass: String,

	@field:SerializedName("id gambar")
	val idGambar: Int
)
