package com.lyrafelipe.shareddemo.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyrafelipe.shareddemo.R
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ReposViewModel(
    private val getReposUseCase: GetReposUseCase
) : ViewModel() {

    private val state = MutableLiveData<ReposViewState>()

    init {
        if (state.value == null) loadRepos()
    }

    fun getViewState(): LiveData<ReposViewState> = state

    fun loadRepos() {
        viewModelScope.launch {
            try {
                val repos = getReposUseCase.execute()
                state.value = if (repos.items.isNotEmpty()) {
                    ReposViewState.Success(repos.items)
                } else {
                    ReposViewState.Empty
                }
            } catch (httpException: HttpException) {
                state.value = ReposViewState.Error(R.string.error_generic)
            } catch (ioException: IOException) {
                state.value = ReposViewState.Error(R.string.error_network)
            }
        }
    }
}
