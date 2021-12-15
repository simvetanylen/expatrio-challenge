package com.expatrio.challenge.application.rest.dto

data class PageDto<T>(
    val pageNumber: Int,
    val size: Int,
    val items: List<T>
)