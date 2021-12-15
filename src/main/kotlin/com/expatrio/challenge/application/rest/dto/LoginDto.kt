package com.expatrio.challenge.application.rest.dto

import javax.validation.constraints.Email

data class LoginDto(
    @field:Email
    val email: String,

    val password: String
)