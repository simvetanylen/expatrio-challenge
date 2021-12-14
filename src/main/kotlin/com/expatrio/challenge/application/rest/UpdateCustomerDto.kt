package com.expatrio.challenge.application.rest

data class UpdateCustomerDto(
    val firstname: String,
    val lastname: String,
    val password: String,
    val description: String?
)