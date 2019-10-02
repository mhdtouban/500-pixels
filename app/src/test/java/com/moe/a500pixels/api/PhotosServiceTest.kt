package com.moe.a500pixels.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moe.a500pixels.popular.data.User
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class PhotosServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: PhotosService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotosService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestPhotos() {
        runBlocking {
            enqueueResponse("popular-photos.json")
            val resultResponse = service.getPhotos().body()

            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/photos"))
        }
    }

    @Test
    fun getPhotosResponse() {
        runBlocking {
            enqueueResponse("popular-photos.json")
            val resultResponse = service.getPhotos().body()
            val photos = resultResponse!!.photos

            assertThat(resultResponse.total_items, `is`(2))
            assertThat(photos.size, `is`(2))
        }
    }


    @Test
    fun getPhotosItem() {
        runBlocking {
            enqueueResponse("popular-photos.json")
            val resultResponse = service.getPhotos().body()
            val photos = resultResponse!!.photos

            val photo = photos[0]
            assertThat(photo.id, `is`(1003772084))
            assertThat(photo.created_at, `is`("2019-10-01T14:56:56+00:00"))
            assertThat(photo.privacy, `is`(false))
            assertThat(photo.name, `is`("Arina"))
            assertThat(
                photo.description,
                `is`("My own postwork method. 15 minutes only. I dont use frequency separation, masks, curves and any complicated methods at all. It's unbelievable simple and very fast. Personal online lessons and video tutorials - outofsight@mail.ru. ")
            )
            assertThat(photo.times_viewed, `is`(32777))
            assertThat(photo.rating, `is`(99.6))
            assertThat(photo.created_at, `is`("2019-10-01T14:56:56+00:00"))
            assertThat(photo.category, `is`(7))
            assertThat(photo.width, `is`(1900))
            assertThat(photo.height, `is`(1425))
            assertThat(photo.comments_count, `is`(16))
            assertThat(photo.nsfw, `is`(false))
            assertThat(
                photo.image_url[0],
                `is`("https://drscdn.500px.org/photo/1003772084/q%3D50_h%3D450/v2?sig=202a7cd25ceb4e2976d689053f749b9d2f263a118d465e03dbdee37dbd1537d0")
            )
            assertThat(
                photo.user, `is`(
                    User(
                        777395,
                        "SeanArcher",
                        "Sean",
                        "Archer",
                        "Yekaterinburg",
                        "Russia",
                        "Sean Archer",
                        "https://drscdn.500px.org/user_avatar/777395/q%3D85_w%3D300_h%3D300/v2?webp=true&v=59&sig=191cb2b0c262728622ed985a893aab9b7d79de4bc78b423725e63044343957b3",
                        3
                    )
                )
            )

        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(
                source.readString(Charsets.UTF_8)
            )
        )
    }
}
