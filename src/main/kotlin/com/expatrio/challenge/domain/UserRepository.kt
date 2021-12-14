package com.expatrio.challenge.domain

interface UserRepository {
    fun findById(id: String): User?
    fun create(user: User): User
    fun delete(id: String): Boolean
    fun update(user: User): User
}