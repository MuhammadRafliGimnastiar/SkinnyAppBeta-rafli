package com.gimnastiar.skinnyappbeta.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class HistoryResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("Daftar History")
	val daftarHistory: List<DaftarHistoryItem>,

	@field:SerializedName("Error")
	val error: Boolean
)

@Parcelize
data class Image(

	@field:SerializedName("signed_url")
	val signedUrl: String,

	@field:SerializedName("class_Predicted")
	val classPredicted: Int
) : Parcelable


@Parcelize
data class DaftarHistoryItem(

	@field:SerializedName("image")
	val image: Image,

	@field:SerializedName("Penanganan")
	val penanganan: Penanganan
) : Parcelable

