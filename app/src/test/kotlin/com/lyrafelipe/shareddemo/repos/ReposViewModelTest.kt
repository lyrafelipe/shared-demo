package com.lyrafelipe.shareddemo.repos

import com.google.common.truth.Truth.assertThat
import com.lyrafelipe.shareddemo.InstantExecutionRule
import com.lyrafelipe.shareddemo.R
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class ReposViewModelTest {

    @get:Rule
    val instantExecutionRule = InstantExecutionRule()

    private val mockedGetReposUseCase = mockk<GetReposUseCase>()
    private val reposViewModel by lazy {
        ReposViewModel(
            mockedGetReposUseCase
        )
    }

    @Test
    fun givenHttpException_whenLoadingRepos_shouldEmitGenericErrorState() {
        coEvery { mockedGetReposUseCase.execute() } throws generateReposErrorResponse()

        reposViewModel.loadRepos()

        assertThat(
            reposViewModel.getViewState().value
        ).isEqualTo(ReposViewState.Error(R.string.error_generic))
    }

    @Test
    fun givenIoException_whenLoadingRepos_shouldEmitNetworkErrorState() {
        coEvery { mockedGetReposUseCase.execute() } throws IOException()

        reposViewModel.loadRepos()

        assertThat(
            reposViewModel.getViewState().value
        ).isEqualTo(ReposViewState.Error(R.string.error_network))
    }

    @Test
    fun givenEmptyReposList_whenLoadingRepos_shouldEmitSuccessState() {
        coEvery { mockedGetReposUseCase.execute() } returns generateEmptyRepos()

        reposViewModel.loadRepos()

        assertThat(reposViewModel.getViewState().value).isEqualTo(ReposViewState.Empty)
    }

    @Test
    fun givenRepos_whenLoadingRepos_shouldEmitSuccessState() {
        val repos = generateRepos()
        coEvery { mockedGetReposUseCase.execute() } returns repos

        reposViewModel.loadRepos()

        assertThat(
            reposViewModel.getViewState().value
        ).isEqualTo(ReposViewState.Success(repos.items))
    }
}

