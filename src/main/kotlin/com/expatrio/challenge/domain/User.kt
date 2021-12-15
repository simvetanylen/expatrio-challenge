package com.expatrio.challenge.domain

import com.expatrio.challenge.domain.exception.BadRequestException
import com.expatrio.challenge.domain.exception.ForbiddenException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

data class User(
    val id: String,
    val role: String,
    var encryptedPassword: String? = null,
    val firstname: String,
    val lastname: String,
    val email: String,
    val description: String?
) {
    companion object {
        private val PASSWORD_ENCODER = BCryptPasswordEncoder()
    }

    fun changePassword(newPassword: String) {
        encryptedPassword = PASSWORD_ENCODER.encode(newPassword)
    }

    fun verifyPassword(submittedPassword: String) {
        val matches = PASSWORD_ENCODER.matches(submittedPassword, encryptedPassword)

        if (!matches) {
            throw BadRequestException()
        }

        if (role != Role.ADMIN) {
            // If user isn't an admin, he can't log in.
            throw ForbiddenException()
        }
    }
}