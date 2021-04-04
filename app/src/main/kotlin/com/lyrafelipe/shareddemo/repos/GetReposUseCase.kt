package com.lyrafelipe.shareddemo.repos

import com.lyrafelipe.shareddemo.remote.GitHubApi

class GetReposUseCase(
    private val api: GitHubApi
) {

    suspend fun execute() = api.getRepos()
}
