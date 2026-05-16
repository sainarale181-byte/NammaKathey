package com.nammakatheey.app.data.model

data class Hero(
    val name: String,
    val type: String,
    val era: String,
    val description: String = "",
    val imageUrl: String = "",
    val district: String = "",
    val badgeEarned: Boolean = false
)