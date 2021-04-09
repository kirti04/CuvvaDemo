package com.cuvva.findyourdog.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.MethodSorters
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
abstract class ViewModelTest : TestCoroutineScope by TestCoroutineScope() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val coroutineDispatcherRule =
        CoroutineDispatcherRule()

    override val coroutineContext: CoroutineContext
        get() = coroutineDispatcherRule.coroutineContext
}

@ExperimentalCoroutinesApi
class CoroutineDispatcherRule : TestWatcher(), CoroutineScope {

    private val testDispatcher = TestCoroutineDispatcher()

    override val coroutineContext: CoroutineContext
        get() = testDispatcher

    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
