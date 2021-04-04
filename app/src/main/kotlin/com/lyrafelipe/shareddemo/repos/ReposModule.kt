package com.lyrafelipe.shareddemo.repos

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val reposModule = module {
    factory { ReposAdapter() }

    viewModel {
        ReposViewModel(
            get()
        )
    }
}
