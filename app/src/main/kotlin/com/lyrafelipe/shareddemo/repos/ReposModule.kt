package com.lyrafelipe.shareddemo.repos

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val reposModule = module {
    factory { ReposAdapter() }
    factory { GetReposUseCase(get()) }
    viewModel {
        ReposViewModel(
            get()
        )
    }
}
