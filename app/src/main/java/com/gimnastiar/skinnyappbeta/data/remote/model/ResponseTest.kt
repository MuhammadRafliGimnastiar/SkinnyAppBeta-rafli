package com.gimnastiar.skinnyappbeta.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseTest(

	@field:SerializedName("Error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("username")
	val username: String
)
