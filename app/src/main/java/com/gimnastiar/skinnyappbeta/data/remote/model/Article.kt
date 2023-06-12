package com.gimnastiar.skinnyappbeta.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val photo: Int,
    val title: String,
    val description: String,
    val textHandler: String
) : Parcelable
