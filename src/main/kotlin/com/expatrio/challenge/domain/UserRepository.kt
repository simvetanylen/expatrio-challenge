package com.expatrio.challenge.domain

interface UserRepository {
    fun create(user: User): User
    fun findByEmail(email: String): User?
    fun findAllByRole(role: String): List<User>
}