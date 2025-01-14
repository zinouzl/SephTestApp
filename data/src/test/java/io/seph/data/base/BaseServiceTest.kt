package io.seph.data.base

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * An abstract base class for service tests that provides a mock web server and a configured Retrofit instance.
 *
 * This class handles the setup and teardown of a [MockWebServer] for simulating API responses,
 * and provides a [Retrofit] instance configured to use the mock server's URL and a JSON converter.
 *
 *  Services Classes should extend this class to create tests for specific services, using [retrofit]
 *  instance to create API interfaces.
 *
 *  The [server] property provides a [MockWebServer] instance which can be used to enqueue mock responses,
 *  allowing for controlled testing of API client behavior under various scenarios.
 *
 *  The [retrofit] property provides a [Retrofit] instance pre-configured to communicate with the [server]
 *  using JSON format.
 *
 *  The [setupServer] function is called before each test to start the server.
 *
 *  The [shutServer] function is called after each test to shutdown the server.
 */
abstract class BaseServiceTest {

    protected val server: MockWebServer by lazy {
        MockWebServer()
    }

    val retrofit: Retrofit by lazy {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Before
    fun setupServer() {
        server.start()
    }

    @After
    fun shutServer() {
        server.shutdown()
    }
}