package com.expatrio.challenge.application.rest

import com.expatrio.challenge.domain.Customer
import com.expatrio.challenge.domain.CustomerService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("customers")
class CustomerWebService(
    private val customerService: CustomerService
) {

    @GetMapping
    fun getAll(): List<CustomerDto> {
        return customerService.getAll().map {
            it.toDto()
        }
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: UUID): CustomerDto {
        return customerService.get(id.toString()).toDto()
    }

    @PostMapping
    fun create(@RequestBody dto: CreateCustomerDto): CustomerDto {
        return customerService.create(
            firstname = dto.firstname,
            lastname = dto.lastname,
            email = dto.email,
            password = dto.password,
            description = dto.description
        ).toDto()
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: UUID, @RequestBody dto: UpdateCustomerDto): CustomerDto {
        return customerService.update(
            id = id.toString(),
            firstname = dto.firstname,
            lastname = dto.lastname,
            password = dto.password,
            description = dto.description
        ).toDto()
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: UUID) {
        return customerService.delete(id.toString())
    }

    private fun Customer.toDto(): CustomerDto {
        return CustomerDto(
            id = id,
            lastname = lastname,
            firstname = firstname,
            email = email,
            description = description
        )
    }
}