package com.expatrio.challenge.application.rest

import com.expatrio.challenge.application.rest.dto.CustomerDto
import com.expatrio.challenge.application.rest.dto.UpdateCustomerDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CustomerWebServiceTests(
    @Autowired private val restTemplate: TestRestTemplate,
) {

    @Test
    fun `Simple create test`() {
        val headers = restTemplate.loginAndGetHeaders()

        val entity = restTemplate.createCustomer(createCustomerDto(), headers)

        assertThat(entity.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(entity.body?.firstname).isEqualTo("test")
    }

    @Test
    fun `Create two customers with same email should fail`() {
        val headers = restTemplate.loginAndGetHeaders()

        val dto = createCustomerDto()
        restTemplate.createCustomer(dto, headers)
        val entity = restTemplate.createCustomer(dto, headers, Any::class.java)

        // TODO : status code should be 400
        assertThat(entity.statusCode).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @Test
    fun `Create a customer without setting credentials should fail`() {
        val entity = restTemplate.createCustomer(createCustomerDto(), null, Any::class.java)

        assertThat(entity.statusCode).isEqualTo(HttpStatus.FORBIDDEN)
    }

    @Test
    fun `Create a customer with a wrong email should fail`() {
        val headers = restTemplate.loginAndGetHeaders()

        val entity = restTemplate.createCustomer(
            dto = createCustomerDto().copy(email = "not an email"),
            headers = headers,
            responseType = Any::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `Simple update test`() {
        val headers = restTemplate.loginAndGetHeaders()

        val customerId = restTemplate.createCustomer(createCustomerDto(), headers).body?.id!!

        val entity = restTemplate.exchange(
            URI("/customers/$customerId"),
            HttpMethod.PUT,
            HttpEntity(
                UpdateCustomerDto(
                    firstname = "test2",
                    lastname = "test2",
                    description = "new description"
                ),
                headers
            ),
            CustomerDto::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body!!.firstname).isEqualTo("test2")
    }

    @Test
    fun `Simple delete test`() {
        val headers = restTemplate.loginAndGetHeaders()

        val customerId = restTemplate.createCustomer(createCustomerDto(), headers).body?.id!!

        val entity = restTemplate.exchange(
            URI("/customers/$customerId"),
            HttpMethod.DELETE,
            HttpEntity(
                null,
                headers
            ),
            String::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }
}