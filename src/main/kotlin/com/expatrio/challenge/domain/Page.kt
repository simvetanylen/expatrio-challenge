package com.expatrio.challenge.domain

data class Page<T>(
    val pageNumber: Int,
    val size: Int,
    val items: List<T>
)