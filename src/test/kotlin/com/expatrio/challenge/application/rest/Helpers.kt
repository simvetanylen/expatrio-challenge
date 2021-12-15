package com.expatrio.challenge.application.rest

import com.expatrio.challenge.application.rest.dto.CreateCustomerDto
import com.expatrio.challenge.application.rest.dto.CustomerDto
import com.expatrio.challenge.application.rest.dto.LoginDto
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import java.net.URI
import java.util.*

fun TestRestTemplate.loginAndGetHeaders(): HttpHeaders {
    val entity = this.postForEntity(
        URI("/auth/login"),
        LoginDto(
            email = "admin@exemple.com",
            password = "password"
        ),
        String::class.java
    )

    val token = entity.headers["Set-Cookie"]!!
        .first { it.startsWith("Authorization") }
        .split(";")
        .first()
        .removePrefix("Authorization=Bearer ")

    val headers = HttpHeaders()
    headers.setBearerAuth(token)

    return headers
}

fun TestRestTemplate.createCustomer(
    dto: CreateCustomerDto,
    headers: HttpHeaders? = null
): ResponseEntity<CustomerDto> {
    return this.createCustomer(dto, headers, CustomerDto::class.java)
}

fun <T> TestRestTemplate.createCustomer(
    dto: CreateCustomerDto,
    headers: HttpHeaders?,
    responseType: Class<T>
): ResponseEntity<T> {
    return this.postForEntity(
        URI("/customers"),
        HttpEntity(dto, headers),
        responseType
    )
}

fun createCustomerDto() = CreateCustomerDto(
    email = "${UUID.randomUUID()}@exemple.com",
    firstname = "test",
    lastname = "test",
    password = "password",
    description = "test"
)