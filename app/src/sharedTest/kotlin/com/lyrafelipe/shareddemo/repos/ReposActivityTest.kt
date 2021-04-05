package com.lyrafelipe.shareddemo.repos

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockkClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.mock.MockProviderRule

@RunWith(AndroidJUnit4::class)
class ReposActivityTest {

    @get:Rule
    val mockProvider = MockProviderRule.create {
        mockkClass(it)
    }

    @Test
    fun givenGenericError_whenLoadingRepos_shouldShowGenericErrorMessage() {
        ReposArrange {
            this.setupErrorResponse()
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
            this.setupRetryErrorResponse()
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
