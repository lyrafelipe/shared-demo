package com.lyrafelipe.shareddemo.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repos(
    @SerialName("items") val items: List<Repo>
)
