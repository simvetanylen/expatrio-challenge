package com.expatrio.challenge.application.rest.dto

data class CustomerDto(
    val id: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val description: String?
)