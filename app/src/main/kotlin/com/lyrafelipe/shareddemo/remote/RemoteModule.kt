package com.lyrafelipe.shareddemo.remote

import org.koin.dsl.module

val remoteModule = module {
    single { GitHubApiClient.createGitHubApiClient() }
}
