package com.lyrafelipe.shareddemo.repos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lyrafelipe.shareddemo.R
import com.lyrafelipe.shareddemo.databinding.ReposActivityBinding
import com.lyrafelipe.shareddemo.models.Repo
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReposActivity : AppCompatActivity() {
    private val reposViewModel: ReposViewModel by viewModel()
    private val reposAdapter: ReposAdapter by inject()
    private val reposBinding by lazy { ReposActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(reposBinding.root)

        reposBinding.repos.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        reposBinding.repos.adapter = reposAdapter

        reposViewModel.getViewState().observe(this, Observer {
            when (it) {
                is ReposViewState.Success -> showRepos(it.repos)
                is ReposViewState.Empty -> showEmptyList()
                is ReposViewState.Error -> showError(it.errorResId)
            }
        })

        reposBinding.reposError.retry.setOnClickListener {
            reposBinding.reposFlipper.displayedChild = 0
            reposViewModel.loadRepos()
        }
    }

    private fun showRepos(repos: List<Repo>) {
        reposAdapter.submitList(repos)
        reposBinding.reposFlipper.displayedChild = 1
    }

    private fun showEmptyList() {
        reposBinding.reposError.image.setImageResource(R.drawable.ic_empty)
        reposBinding.reposError.message.text = getString(R.string.error_empty)
        reposBinding.reposFlipper.displayedChild = 2
    }

    private fun showError(errorResId: Int) {
        reposBinding.reposError.image.setImageResource(R.drawable.ic_error)
        reposBinding.reposError.message.text = getString(errorResId)
        reposBinding.reposFlipper.displayedChild = 2
    }
}
