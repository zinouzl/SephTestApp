package io.seph.data.base

import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

/**
 * Base class for unit tests that use coroutines.
 *
 * This class provides a [testDispatcher] that can be used for running coroutines in tests.
 * It also sets up and tears down the main dispatcher for testing purposes.
 *
 * The [coRunTest] function provides a way to run coroutine-based tests.
 * it is also possible to use the predefined [runTest] function.
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseTest {

    protected val testDispatcher = StandardTestDispatcher()

    @Before
    open fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    protected fun coRunTest(testBody: suspend TestScope.() -> Unit): TestResult =
        runTest(testDispatcher, testBody = testBody)
}