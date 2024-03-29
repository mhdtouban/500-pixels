package com.moe.a500pixels.popular.data

import com.moe.a500pixels.api.PhotosService
import com.moe.a500pixels.api.BaseDataSource
import com.moe.a500pixels.testing.OpenForTesting
import javax.inject.Inject

/**
 * Works with the Photo API to get data.
 */
@OpenForTesting
class PhotoRemoteDataSource @Inject constructor(private val service: PhotosService) :
    BaseDataSource() {

    suspend fun fetchPhotos(consumerKey: String, feature: String, imageSize: Int, page: Int) =
        getResult { service.getPhotos(consumerKey, feature, imageSize, page) }

}
