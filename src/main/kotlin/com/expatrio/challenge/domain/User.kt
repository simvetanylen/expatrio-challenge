package com.expatrio.challenge.domain

import com.expatrio.challenge.domain.exception.BadRequestException
import com.expatrio.challenge.domain.exception.ForbiddenException
import com.expatrio.challenge.domain.exception.UnauthorizedException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

data class User(
    val id: String,
    val role: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val description: String?
) {
    companion object {
        private val PASSWORD_ENCODER = BCryptPasswordEncoder()
    }

    fun verifyPassword(submittedPassword: String) {
        val matches = PASSWORD_ENCODER.matches(submittedPassword, password)

        if (!matches) {
            throw BadRequestException()
        }

        if (role != Role.ADMIN) {
            // If user isn't an admin, he can't log in.
            throw ForbiddenException()
        }
    }
}