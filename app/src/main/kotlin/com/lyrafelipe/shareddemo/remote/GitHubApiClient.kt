package com.lyrafelipe.shareddemo.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object GitHubApiClient {

    private const val BASE_URL = "https://api.github.com/"
    private val contentType = "application/json".toMediaType()

    @OptIn(ExperimentalSerializationApi::class)
    fun createGitHubApiClient(): GitHubApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
            .create(GitHubApi::class.java)
    }
}
