package com.kongjak.kongjak.models

data class BlogPostMetadata(
    val title: String,
    val route: String,
    val date: String,
    val description: String,
    val tags: List<String>,
    val categorySlug: String?,
    val categoryName: String?,
)
