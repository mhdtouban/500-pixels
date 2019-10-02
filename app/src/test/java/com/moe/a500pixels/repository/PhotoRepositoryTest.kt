package com.moe.a500pixels.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moe.a500pixels.api.PhotosService
import com.moe.a500pixels.data.AppDatabase
import com.moe.a500pixels.popular.data.PhotoDao
import com.moe.a500pixels.popular.data.PhotoRemoteDataSource
import com.moe.a500pixels.popular.data.PhotoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class PhotoRepositoryTest {
    private lateinit var repository: PhotoRepository
    private val dao = mock(PhotoDao::class.java)
    private val service = mock(PhotosService::class.java)
    private val remoteDataSource = PhotoRemoteDataSource(service)
    private val mockRemoteDataSource = spy(remoteDataSource)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.photoDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = PhotoRepository(dao, remoteDataSource)
    }

    @Test
    fun loadLegoSetsFromNetwork() {
        runBlocking {
            repository.observePagedPhotos(
                connectivityAvailable = true,
                coroutineScope = coroutineScope
            )

            verify(dao, never()).getPhotos()
            verifyZeroInteractions(dao)
        }
    }

}