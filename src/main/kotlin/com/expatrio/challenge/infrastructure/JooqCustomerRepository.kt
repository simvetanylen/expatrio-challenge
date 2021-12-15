package com.expatrio.challenge.infrastructure

import com.expatrio.challenge.domain.Customer
import com.expatrio.challenge.domain.CustomerRepository
import com.expatrio.challenge.domain.Role
import com.expatrio.challenge.generated.jooq.tables.AppUser
import com.expatrio.challenge.generated.jooq.tables.records.AppUserRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class JooqCustomerRepository(
    private val jooq: DSLContext
) : CustomerRepository {

    override fun findAll(): List<Customer> {
        val record = jooq.fetch(
            AppUser.APP_USER,
            AppUser.APP_USER.ROLE.eq(Role.CUSTOMER)
        )

        return record.map {
            it.toDomain()
        }
    }

    override fun findById(id: String): Customer? {
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

    override fun create(customer: Customer): Customer {
        val record = jooq.newRecord(AppUser.APP_USER)

        record.apply {
            id = customer.id
            creationTime = LocalDateTime.now()
            updateTime = LocalDateTime.now()
            role = Role.CUSTOMER
            password = customer.password
            firstname = customer.firstname
            lastname = customer.lastname
            email = customer.email
            description = customer.description
        }

        record.store()
        return record.toDomain()
    }

    override fun update(customer: Customer): Customer {
        val record = fetchById(customer.id) ?: throw Exception("User not found.")

        record.apply {
            updateTime = LocalDateTime.now()
            role = Role.CUSTOMER
            password = customer.password
            firstname = customer.firstname
            lastname = customer.lastname
            email = customer.email
            description = customer.description
        }

        record.store()
        return record.toDomain()
    }

    private fun fetchById(id: String): AppUserRecord? {
        val record = jooq.fetchOptional(
            AppUser.APP_USER,
            AppUser.APP_USER.ID.eq(id)
        )

        return if (record.isPresent) {
            val user = record.get()

            if (user.role != Role.CUSTOMER) {
                // TODO : what to do in this case ?
                throw Exception("Not a customer.")
            }

            user
        } else {
            null
        }
    }

    private fun AppUserRecord.toDomain(): Customer {
        return Customer(
            id = this.id,
            password = this.password,
            firstname = this.firstname,
            lastname = this.lastname,
            email = this.email,
            description = this.description
        )
    }
}