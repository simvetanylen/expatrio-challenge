package com.expatrio.challenge.domain

import java.util.*

data class Subject(
    val authenticated: Boolean,
    val id: UUID?,
    val role: String?
) {
    init {
        if (!authenticated && (id != null || role != null)) {
            throw Exception("Unauthenticated subject must have empty attributes.")
        }
    }

    companion object {
        fun unauthenticated(): Subject {
            return Subject(
                authenticated = false,
                id = null,
                role = null
            )
        }
    }
}