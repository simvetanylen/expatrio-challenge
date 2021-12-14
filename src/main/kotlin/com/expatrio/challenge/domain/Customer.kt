package com.expatrio.challenge.domain

data class Customer(
    val id: String,
    var password: String,
    var firstname: String,
    var lastname: String,
    val email: String,
    var description: String?
)