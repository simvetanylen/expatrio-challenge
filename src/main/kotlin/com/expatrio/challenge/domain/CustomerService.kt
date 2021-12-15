package com.expatrio.challenge.domain

import com.expatrio.challenge.domain.exception.NotFoundException
import org.springframework.security.access.annotation.Secured
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {

    companion object {
        private val PASSWORD_ENCODER = BCryptPasswordEncoder()
    }

    @Transactional(readOnly = true)
    @Secured(Role.ADMIN)
    fun getAll(): List<Customer> {
        return customerRepository.findAll()
    }

    @Transactional(readOnly = true)
    @Secured(Role.ADMIN)
    fun get(id: String): Customer {
        return customerRepository.findById(id) ?: throw NotFoundException()
    }

    @Transactional
    @Secured(Role.ADMIN)
    fun create(
        firstname: String,
        lastname: String,
        password: String,
        email: String,
        description: String?
    ): Customer {
        return customerRepository.create(
            Customer(
                id = UUID.randomUUID().toString(),
                password = PASSWORD_ENCODER.encode(password),
                firstname = firstname,
                lastname = lastname,
                email = email,
                description = description
            )
        )
    }

    @Transactional
    @Secured(Role.ADMIN)
    fun update(
        id: String,
        firstname: String,
        lastname: String,
        password: String,
        description: String?
    ): Customer {
        val customer = customerRepository.findById(id) ?: throw NotFoundException()

        customer.firstname = firstname
        customer.lastname = lastname
        customer.password = PASSWORD_ENCODER.encode(password)
        customer.description = description

        return customerRepository.update(customer)
    }

    @Transactional
    @Secured(Role.ADMIN)
    fun delete(id: String) {
        customerRepository.delete(id)
    }
}
