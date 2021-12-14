package com.expatrio.challenge.domain

interface CustomerRepository {
    fun findById(id: String): Customer?
    fun delete(id: String): Boolean
    fun create(customer: Customer): Customer
    fun update(customer: Customer): Customer
    fun findAll(): List<Customer>
}