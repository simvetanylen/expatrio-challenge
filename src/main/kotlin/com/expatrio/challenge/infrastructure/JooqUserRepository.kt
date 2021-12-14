package com.expatrio.challenge.infrastructure

import com.expatrio.challenge.domain.Role
import com.expatrio.challenge.domain.User
import com.expatrio.challenge.domain.UserRepository
import com.expatrio.challenge.generated.jooq.tables.AppUser.APP_USER
import com.expatrio.challenge.generated.jooq.tables.records.AppUserRecord
import org.jooq.DSLContext
import org.jooq.Result
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*


@Repository
class JooqUserRepository(
    private val jooq: DSLContext
) : UserRepository {

    override fun findById(id: String): User? {
        return fetchById(id)?.toDomain()
    }

    override fun delete(id: String): Boolean {
        val record = fetchById(id)

        return if (record != null) {
            record.delete()
            true
        } else {
            false
        }
    }

    override fun create(user: User): User {
        val record = jooq.newRecord(APP_USER)

        record.apply {
            id = user.id
            creationTime = LocalDateTime.now()
            updateTime = LocalDateTime.now()
            role = user.role.name
            password = user.password
            firstname = user.firstname
            lastname = user.lastname
            email = user.email
            description = user.description
        }

        record.store()
        return record.toDomain()
    }

    override fun update(user: User): User {
        val record = fetchById(user.id) ?: throw Exception("User not found.")

        record.apply {
            updateTime = LocalDateTime.now()
            role = user.role.name
            password = user.password
            firstname = user.firstname
            lastname = user.lastname
            email = user.email
            description = user.description
        }

        record.store()
        return record.toDomain()
    }

    private fun fetchById(id: String): AppUserRecord? {
        val record = jooq.fetchOptional(APP_USER, APP_USER.ID.eq(id))

        return if (record.isPresent) {
            record.get()
        } else {
            null
        }
    }

    private fun AppUserRecord.toDomain(): User {
        return User(
            id = this.id,
            role = Role.valueOf(this.role),
            password = this.password,
            firstname = this.firstname,
            lastname = this.lastname,
            email = this.email,
            description = this.description
        )
    }
}