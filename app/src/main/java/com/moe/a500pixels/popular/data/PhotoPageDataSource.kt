package com.moe.a500pixels.popular.data

import androidx.paging.PageKeyedDataSource
import com.moe.a500pixels.BuildConfig
import com.moe.a500pixels.data.Result.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Data source for photos pagination via paging library
 */
class PhotoPageDataSource @Inject constructor(
    private val dataSource: PhotoRemoteDataSource,
    private val dao: PhotoDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Photo>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {
        fetchData(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        val page = params.key
        fetchData(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        val page = params.key
        fetchData(page) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, callback: (List<Photo>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response =
                dataSource.fetchPhotos(consumerKey = BuildConfig.CONSUMER_KEY, feature = "popular", imageSize = 200,
                    page = page
                )
            if (response.status == Status.SUCCESS) {
                val results = response.data!!.photos
                dao.insertAll(results)
                callback(results)
            } else if (response.status == Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        Timber.e("An error happened: $message")
        // TODO network error handling
        //networkState.postValue(NetworkState.FAILED)
    }

}