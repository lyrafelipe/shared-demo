package com.lyrafelipe.shareddemo.repos

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lyrafelipe.sharedtestcode.repos.ReposAct
import com.lyrafelipe.sharedtestcode.repos.ReposArrange
import com.lyrafelipe.sharedtestcode.repos.ReposAssert
import com.lyrafelipe.sharedtestcode.rules.MockProviderRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReposActivityTest {

    @get:Rule
    val mockProviderRule = MockProviderRule()

    @Test
    fun givenGenericError_whenLoadingRepos_shouldShowGenericErrorMessage() {
        ReposArrange {
            setupErrorResponse()
        }

        ReposAct {
            launchReposActivity()
        }

        ReposAssert {
            checkGenericErrorMessageDisplayed()
        }
    }

    @Test
    fun givenNetworkError_whenLoadingRepos_shouldShowNetworkErrorMessage() {
        ReposArrange {
            setupNoResponse()
        }

        ReposAct {
            launchReposActivity()
        }

        ReposAssert {
            checkNetworkErrorMessageDisplayed()
        }
    }

    @Test
    fun givenEmptyReposList_whenLoadingRepos_shouldShowNetworkEmptyList() {
        ReposArrange {
            setupEmptyResponse()
        }

        ReposAct {
            launchReposActivity()
        }

        ReposAssert {
            checkEmptyMessageDisplayed()
        }
    }

    @Test
    fun givenError_whenLoadingRepos_shouldRetryLoading() {
        ReposArrange {
            setupRetryErrorResponse()
        }

        ReposAct {
            launchReposActivity()
            clickRetry()
            performScroll(29)
        }

        ReposAssert {
            checkRepoNameDisplayed("Repo 30")
        }
    }

    @Test
    fun givenEmptyReposList_whenLoadingRepos_shouldRetryLoading() {
        ReposArrange {
            setupRetryEmptyResponse()
        }

        ReposAct {
            launchReposActivity()
            clickRetry()
            performScroll(29)
        }

        ReposAssert {
            checkRepoNameDisplayed("Repo 30")
        }
    }

    @Test
    fun givenRepos_whenLoadingRepos_shouldShowRepos() {
        ReposArrange {
            setupReposResponse()
        }

        ReposAct {
            launchReposActivity()
            performScroll(29)
        }

        ReposAssert {
            checkRepoNameDisplayed("Repo 30")
        }
    }
}
