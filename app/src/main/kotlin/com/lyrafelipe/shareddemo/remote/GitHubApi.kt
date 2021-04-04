package com.lyrafelipe.shareddemo.remote

import com.lyrafelipe.shareddemo.models.Repos
import retrofit2.http.GET

interface GitHubApi {

    @GET("search/repositories?q=android+language:Kotlin&sort=stars&page=1")
    suspend fun getRepos(): Repos
}
