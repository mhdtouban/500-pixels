package com.moe.a500pixels.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moe.a500pixels.popular.data.PhotoRepository
import com.moe.a500pixels.popular.ui.PhotosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class PhotoViewModelTest {


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = mock(PhotoRepository::class.java)
    private var viewModel = PhotosViewModel(repository, coroutineScope)

    @Test
    fun testNull() {
        assertThat(viewModel.connectivityAvailable, notNullValue())
        verify(repository, never()).observePagedPhotos(false, coroutineScope)
        verify(repository, never()).observePagedPhotos(true, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObservers() {
        verify(repository, never()).observePagedPhotos(false, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {
        viewModel.connectivityAvailable = true

        verify(repository, never()).observePagedPhotos(true, coroutineScope)
    }

}