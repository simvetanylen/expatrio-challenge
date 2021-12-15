package com.expatrio.challenge.application.rest.dto

data class UpdateCustomerDto(
    val firstname: String,
    val lastname: String,
    val password: String,
    val description: String?
)