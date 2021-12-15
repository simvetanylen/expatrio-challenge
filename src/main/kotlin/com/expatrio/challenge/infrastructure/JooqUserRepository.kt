package com.expatrio.challenge.infrastructure

import com.expatrio.challenge.domain.User
import com.expatrio.challenge.domain.UserRepository
import com.expatrio.challenge.generated.jooq.tables.AppUser.APP_USER
import com.expatrio.challenge.generated.jooq.tables.records.AppUserRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class JooqUserRepository(
    private val jooq: DSLContext
) : UserRepository {

    override fun findByEmail(email: String): User? {
        val record = jooq.fetchOptional(APP_USER, APP_USER.EMAIL.eq(email))

        return if (record.isPresent) {
            record.get().toDomain()
        } else {
            null
        }
    }

    override fun findAllByRole(role: String): List<User> {
        val record = jooq.fetch(APP_USER, APP_USER.ROLE.eq(role))

        return record.map {
            it.toDomain()
        }
    }

    override fun create(user: User): User {
        val record = jooq.newRecord(APP_USER)

        record.apply {
            id = user.id
            creationTime = LocalDateTime.now()
            updateTime = LocalDateTime.now()
            role = user.role
            password = user.password
            firstname = user.firstname
            lastname = user.lastname
            email = user.email
            description = user.description
        }

        record.store()
        return record.toDomain()
    }

    private fun AppUserRecord.toDomain(): User {
        return User(
            id = this.id,
            role = this.role,
            password = this.password,
            firstname = this.firstname,
            lastname = this.lastname,
            email = this.email,
            description = this.description
        )
    }
}