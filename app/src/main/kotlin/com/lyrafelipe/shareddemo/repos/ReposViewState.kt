package com.lyrafelipe.shareddemo.repos

import com.lyrafelipe.shareddemo.models.Repo

sealed class ReposViewState {
    data class Error(val errorResId: Int) : ReposViewState()
    data class Success(val repos: List<Repo>) : ReposViewState()
    object Empty : ReposViewState()
}
