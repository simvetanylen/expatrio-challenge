package com.expatrio.challenge.domain

import java.util.*

interface CustomerRepository {
    fun findById(id: UUID): Customer?
    fun delete(id: UUID): Boolean
    fun create(customer: Customer): Customer
    fun update(customer: Customer): Customer
    fun findAll(): List<Customer>
}