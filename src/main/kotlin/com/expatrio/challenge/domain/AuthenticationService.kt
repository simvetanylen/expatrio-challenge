package com.expatrio.challenge.domain

import com.expatrio.challenge.domain.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthenticationService(
    private val userRepository: UserRepository
) {

    @Transactional(readOnly = true)
    fun login(email: String, password: String): Subject {
        val user = userRepository.findByEmail(email) ?: throw NotFoundException()

        user.verifyPassword(password)

        return Subject(
            authenticated = true,
            id = user.id,
            role = user.role
        )
    }
}