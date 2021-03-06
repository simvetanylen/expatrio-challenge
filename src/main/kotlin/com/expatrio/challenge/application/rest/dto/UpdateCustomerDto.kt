package com.expatrio.challenge.application.rest.dto

import javax.validation.constraints.Size

data class UpdateCustomerDto(
    @field:Size(min = 3, max = 32)
    val firstname: String,

    @field:Size(min = 3, max = 32)
    val lastname: String,

    val description: String?
)