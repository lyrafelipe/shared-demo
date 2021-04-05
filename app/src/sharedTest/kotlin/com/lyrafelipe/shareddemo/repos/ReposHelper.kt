package com.lyrafelipe.shareddemo.repos

import com.lyrafelipe.shareddemo.models.Owner
import com.lyrafelipe.shareddemo.models.Repo
import com.lyrafelipe.shareddemo.models.Repos
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.util.*
import kotlin.random.Random

fun generateReposErrorResponse() = HttpException(
    Response.error<List<Repos>>(
        500,
        "".toResponseBody("application/json".toMediaType())
    )
)

fun generateRepos() = Repos(generateRepoList())

fun generateEmptyRepos() = Repos(listOf())

private fun generateRepoList(): List<Repo> {
    return (1..30).map {
        Repo(
            Random.nextInt(),
            "Repo $it",
            UUID.randomUUID().toString(),
            Random.nextInt(),
            Random.nextInt(),
            Owner(
                Random.nextInt(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
            )
        )
    }
}