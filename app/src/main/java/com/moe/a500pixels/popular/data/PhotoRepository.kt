package com.moe.a500pixels.popular.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class PhotoRepository @Inject constructor(
    private val dao: PhotoDao,
    private val photoRemoteDataSource: PhotoRemoteDataSource
) {

    fun observePagedPhotos(
        connectivityAvailable: Boolean,
        coroutineScope: CoroutineScope
    ) =
        if (connectivityAvailable) observeRemotePagedPhotos(coroutineScope)
        else observeLocalPagedPhotos()

    private fun observeLocalPagedPhotos(): LiveData<PagedList<Photo>> {
        val dataSourceFactory =
            dao.getPagedPhotos()

        return LivePagedListBuilder(
            dataSourceFactory,
            PhotoPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    private fun observeRemotePagedPhotos(ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<Photo>> {
        val dataSourceFactory = PhotoPageDataSourceFactory(
            photoRemoteDataSource,
            dao, ioCoroutineScope
        )

        return LivePagedListBuilder(
            dataSourceFactory,
            PhotoPageDataSourceFactory.pagedListConfig()
        ).build()
    }


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: PhotoRepository? = null

        fun getInstance(dao: PhotoDao, photoRemoteDataSource: PhotoRemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: PhotoRepository(dao, photoRemoteDataSource).also { instance = it }
            }
    }
}
