package com.moe.a500pixels.api

import com.moe.a500pixels.popular.data.Photo
import com.moe.a500pixels.popular.data.PopularPhotoBaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosService {

    companion object {
        const val ENDPOINT = "https://api.500px.com/v1/"
    }

    @GET("photos")
    suspend fun getPhotos(
        @Query("consumer_key") key: String? = null,
        @Query("feature") feature: String? = null,
        @Query("image_size") imageSize: Int? = null,
        @Query("page") page: Int? = null
    ): Response<PopularPhotoBaseResponse<Photo>>
}