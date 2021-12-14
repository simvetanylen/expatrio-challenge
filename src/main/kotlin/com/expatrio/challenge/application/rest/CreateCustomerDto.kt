package com.expatrio.challenge.application.rest

data class CreateCustomerDto(
    val firstname: String,
    val lastname: String,
    val password: String,
    val email: String,
    val description: String?
)