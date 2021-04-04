package com.lyrafelipe.shareddemo.repos

import org.koin.dsl.module

val reposModule = module {
    factory { ReposAdapter() }
}
