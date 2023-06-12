package com.gimnastiar.skinnyappbeta.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@androidx.room.Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)


    @Query("UPDATE favorite SET isFavorite = :value WHERE titleArticle = :title")
    fun setFavorite(value: Boolean, title: String)

    @Query("SELECT * from favorite WHERE isFavorite = :value")
    suspend fun getAllFavorite(value: Boolean): List<Favorite>

    @Query("SELECT * FROM favorite WHERE titleArticle = :title")
    fun getFavoriteUserByUsername(title: String): Favorite

}