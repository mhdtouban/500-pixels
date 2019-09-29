package com.moe.a500pixels.popular.ui

import androidx.lifecycle.ViewModel
import com.moe.a500pixels.di.CoroutineScropeIO
import com.moe.a500pixels.popular.data.PhotoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

/**
 * The ViewModel used in [PhotosFragment].
 */
class PhotosViewModel @Inject constructor(
    private val repository: PhotoRepository,
    @CoroutineScropeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {

    var connectivityAvailable: Boolean = false

    val photos by lazy {
        repository.observePagedPhotos(
            connectivityAvailable, ioCoroutineScope
        )
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}
