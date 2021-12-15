package com.expatrio.challenge.application.rest.dto

import java.util.*

data class CustomerDto(
    val id: UUID,
    val firstname: String,
    val lastname: String,
    val email: String,
    val description: String?
)