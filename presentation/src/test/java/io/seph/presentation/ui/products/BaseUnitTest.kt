package io.seph.presentation.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule
import java.util.Calendar
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseUnitTest : KoinTest {
    protected val testMainDispatcher = StandardTestDispatcher()
    protected open val module = module {
        // by default is empty, instances of all classes suppose to be created manually
        // override and define instances of used classes, if they need to be injected in the test
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(module)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { mockk() }

    @Before
    open fun setUp() {
        Dispatchers.setMain(testMainDispatcher)
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
    }

    protected fun getCalendar(hour: Int, minute: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute

        return calendar
    }

    protected fun <T> testFlow(
        flow: Flow<T>,
        action: suspend TestScope.() -> Unit = {},
        expectation: suspend (result: List<T>) -> Unit
    ) {
        runTest(testMainDispatcher) {
            val results = mutableListOf<T>()
            val job = launch { flow.toList(results) }

            advanceUntilIdle() // wait for flow will be subscribed
            action()
            advanceUntilIdle()

            expectation(results)
            job.cancel()
        }
    }

    protected fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}