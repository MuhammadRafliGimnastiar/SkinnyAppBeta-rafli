package com.gimnastiar.skinnyappbeta.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("Error")
	val error: Boolean,

	@field:SerializedName("Your Username")
	val yourUsername: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("Your Name")
	val yourName: String,

	@field:SerializedName("token")
	val token: String
)
