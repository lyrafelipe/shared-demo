package com.lyrafelipe.shareddemo.repos

import com.google.common.truth.Truth.assertThat
import com.lyrafelipe.shareddemo.InstantExecutionRule
import com.lyrafelipe.shareddemo.R
import com.lyrafelipe.shareddemo.models.Owner
import com.lyrafelipe.shareddemo.models.Repo
import com.lyrafelipe.shareddemo.models.Repos
import io.mockk.coEvery
import io.mockk.mockk
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.*
import kotlin.random.Random

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
        coEvery {
            mockedGetReposUseCase.execute()
        } throws HttpException(generateReposErrorResponse())

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
        val repos = generateEmptyRepos()
        coEvery { mockedGetReposUseCase.execute() } returns repos

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

    private fun generateRepoList(): List<Repo> {
        return listOf(
            Repo(
                Random.nextInt(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                Random.nextInt(),
                Random.nextInt(),
                Owner(
                    Random.nextInt(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString()
                )
            )
        )
    }

    private fun generateReposErrorResponse(): Response<Repos> =
        Response.error(
            500,
            "".toResponseBody("application/json".toMediaType())
        )

    private fun generateRepos() = Repos(generateRepoList())

    private fun generateEmptyRepos() = Repos(listOf())
}

