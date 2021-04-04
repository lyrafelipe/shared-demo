package com.lyrafelipe.shareddemo.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.lyrafelipe.shareddemo.databinding.ReposItemBinding
import com.lyrafelipe.shareddemo.models.Repo

class ReposAdapter : ListAdapter<Repo, ReposAdapter.ReposViewHolder>(ReposDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(
            ReposItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReposViewHolder(
        private val reposItemBinding: ReposItemBinding
    ) : RecyclerView.ViewHolder(reposItemBinding.root) {

        fun bind(repo: Repo) {
            reposItemBinding.name.text = repo.name
            reposItemBinding.description.text = repo.description
            reposItemBinding.forks.text = repo.forksCount.toString()
            reposItemBinding.stars.text = repo.stargazersCount.toString()
            reposItemBinding.avatar.load(repo.owner.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            reposItemBinding.username.text = repo.owner.login
        }
    }

    class ReposDiffCallback : DiffUtil.ItemCallback<Repo>() {

        override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = newItem.id == oldItem.id

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = newItem == oldItem
    }
}
