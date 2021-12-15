package com.expatrio.challenge.domain

import com.expatrio.challenge.domain.exception.NotFoundException
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) {

    @Transactional(readOnly = true)
    @Secured(Role.ADMIN)
    fun getAll(): List<Customer> {
        return customerRepository.findAll()
    }

    @Transactional(readOnly = true)
    @Secured(Role.ADMIN)
    fun get(id: UUID): Customer {
        return customerRepository.findById(id) ?: throw NotFoundException()
    }

    @Transactional
    @Secured(Role.ADMIN)
    fun create(
        firstname: String,
        lastname: String,
        email: String,
        description: String?
    ): Customer {
        return customerRepository.create(
            Customer(
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
        id: UUID,
        firstname: String,
        lastname: String,
        description: String?
    ): Customer {
        val customer = customerRepository.findById(id) ?: throw NotFoundException()

        customer.firstname = firstname
        customer.lastname = lastname
        customer.description = description

        return customerRepository.update(customer)
    }

    @Transactional
    @Secured(Role.ADMIN)
    fun delete(id: UUID) {
        customerRepository.delete(id)
    }
}
