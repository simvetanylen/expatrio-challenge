package com.expatrio.challenge.domain

import java.util.*

data class Customer(
    val id: UUID = UUID.randomUUID(),
    var firstname: String,
    var lastname: String,
    val email: String,
    var description: String?
)