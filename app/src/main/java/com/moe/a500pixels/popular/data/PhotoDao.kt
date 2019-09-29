package com.moe.a500pixels.popular.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the Photo class.
 */
@Dao
interface PhotoDao {

    @Query("SELECT * FROM popular ORDER BY created_at DESC")
    fun getPhotos(): LiveData<List<Photo>>

    @Query("SELECT * FROM popular WHERE id = :id")
    fun getPhoto(id: String): LiveData<Photo>

    @Query("SELECT * FROM popular ORDER BY created_at DESC")
    fun getPagedPhotos(): DataSource.Factory<Int, Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<Photo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photo: Photo)
}
