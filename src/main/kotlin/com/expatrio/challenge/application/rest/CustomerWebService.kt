package com.expatrio.challenge.application.rest

import com.expatrio.challenge.application.rest.dto.CreateCustomerDto
import com.expatrio.challenge.application.rest.dto.CustomerDto
import com.expatrio.challenge.application.rest.dto.PageDto
import com.expatrio.challenge.application.rest.dto.UpdateCustomerDto
import com.expatrio.challenge.domain.Customer
import com.expatrio.challenge.domain.CustomerService
import com.expatrio.challenge.domain.exception.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustomerWebService(
    private val customerService: CustomerService
) {

    @GetMapping
    fun getAll(
        @RequestParam(name = "size", defaultValue = "10") size: Int,
        @RequestParam(name = "pageNumber", defaultValue = "0") pageNumber: Int
    ): PageDto<CustomerDto> {
        if (size < 1) { throw BadRequestException() }
        if (pageNumber < 0) { throw BadRequestException() }

        val customers = customerService.getAll(size = size, pageNumber = pageNumber)

        return PageDto(
            pageNumber = customers.pageNumber,
            size = customers.size,
            items = customers.items.map { it.toDto() }
        )
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: UUID): CustomerDto {
        return customerService.get(id).toDto()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid dto: CreateCustomerDto): CustomerDto {
        return customerService.create(
            firstname = dto.firstname,
            lastname = dto.lastname,
            email = dto.email,
            description = dto.description
        ).toDto()
    }

    @PutMapping("{id}")
    fun update(@PathVariable id: UUID, @RequestBody @Valid dto: UpdateCustomerDto): CustomerDto {
        return customerService.update(
            id = id,
            firstname = dto.firstname,
            lastname = dto.lastname,
            description = dto.description
        ).toDto()
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) {
        return customerService.delete(id)
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
