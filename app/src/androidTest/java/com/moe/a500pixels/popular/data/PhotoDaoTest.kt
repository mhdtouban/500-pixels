package com.moe.a500pixels.popular.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moe.a500pixels.popular.util.getValue
import com.moe.a500pixels.popular.util.testPhotoA
import com.moe.a500pixels.popular.util.testPhotoB
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoDaoTest : DbTest() {
    private lateinit var photoDao: PhotoDao
    private val photoA = testPhotoA.copy()
    private val photoB = testPhotoB.copy()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        photoDao = db.photoDao()

        // Insert photos in non-alphabetical order to test that results are sorted by created_at
        runBlocking {
            photoDao.insertAll(listOf(photoB, photoA))
        }
    }

    @Test
    fun testGetPhotos() {
        val list = getValue(photoDao.getPhotos())
        assertThat(list.size, equalTo(2))

        // Ensure photo list is sorted by created_at
        assertThat(list[0], equalTo(photoA))
        assertThat(list[1], equalTo(photoB))
    }

    @Test
    fun testGetPhoto() {
        assertThat(getValue(photoDao.getPhoto(photoA.id.toString())), equalTo(photoA))
    }
}