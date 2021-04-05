package com.lyrafelipe.shareddemo.repos

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.lyrafelipe.shareddemo.R
import io.mockk.coEvery
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import java.io.IOException

class ReposArrange(action: ReposArrange.() -> Unit) : KoinTest {

    init {
        action.invoke(this)
    }

    fun setupErrorResponse() {
        declareMock<GetReposUseCase> {
            coEvery { execute() } throws generateReposErrorResponse()
        }
    }

    fun setupNoResponse() {
        declareMock<GetReposUseCase> {
            coEvery { execute() } throws IOException()
        }
    }

    fun setupEmptyResponse() {
        declareMock<GetReposUseCase> {
            coEvery { execute() } returns generateEmptyRepos()
        }
    }

    fun setupRetryErrorResponse() {
        declareMock<GetReposUseCase> {
            coEvery { execute() } throws generateReposErrorResponse() andThen generateRepos()
        }
    }

    fun setupRetryEmptyResponse() {
        declareMock<GetReposUseCase> {
            coEvery { execute() } returns generateEmptyRepos() andThen generateRepos()
        }
    }

    fun setupReposResponse() {
        declareMock<GetReposUseCase> {
            coEvery { execute() } returns generateRepos()
        }
    }
}

class ReposAct(action: ReposAct.() -> Unit) {

    init {
        action.invoke(this)
    }

    fun launchReposActivity() {
        ActivityScenario.launch(ReposActivity::class.java)
    }

    fun performScroll(position: Int) {
        onView(withId(R.id.repos))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(position))
    }

    fun clickRetry() {
        onView(withId(R.id.retry))
            .perform(click())
    }
}

class ReposAssert(action: ReposAssert.() -> Unit) {

    init {
        action.invoke(this)
    }

    fun checkRepoNameDisplayed(title: String) {
        onView(withText(title))
            .check(matches(isDisplayed()))
    }

    fun checkGenericErrorMessageDisplayed() {
        onView(withText(R.string.error_generic))
            .check(matches(isDisplayed()))
    }

    fun checkNetworkErrorMessageDisplayed() {
        onView(withText(R.string.error_network))
            .check(matches(isDisplayed()))
    }

    fun checkEmptyMessageDisplayed() {
        onView(withText(R.string.error_empty))
            .check(matches(isDisplayed()))
    }
}
