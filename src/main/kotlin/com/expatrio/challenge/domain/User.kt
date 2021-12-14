package com.expatrio.challenge.domain

data class User(
    val id: String,
    val role: Role,
    val password: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val description: String?
)