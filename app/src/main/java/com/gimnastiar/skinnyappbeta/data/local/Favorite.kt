package com.gimnastiar.skinnyappbeta.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite(
    @PrimaryKey
    var image: Int,
    var titleArticle: String,
    var descArticle: String,
    var descHandleArticle: String,
    var isFavorite: Boolean
) : Parcelable
