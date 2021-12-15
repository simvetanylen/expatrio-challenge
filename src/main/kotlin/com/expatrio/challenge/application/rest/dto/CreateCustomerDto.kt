package com.expatrio.challenge.application.rest.dto

import javax.validation.constraints.Email
import javax.validation.constraints.Size

data class CreateCustomerDto(
    @field:Size(min = 3, max = 32)
    val firstname: String,

    @field:Size(min = 3, max = 32)
    val lastname: String,

    @field:Email
    val email: String,

    val description: String?
)