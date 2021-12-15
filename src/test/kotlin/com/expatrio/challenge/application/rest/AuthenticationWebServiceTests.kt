package com.expatrio.challenge.application.rest

import com.expatrio.challenge.application.rest.dto.LoginDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import java.net.URI


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthenticationWebServiceTests(
    @Autowired private val restTemplate: TestRestTemplate
) {

    @Test
    fun `Simple login test`() {
        val entity = restTemplate.postForEntity(
            URI("/auth/login"),
            LoginDto(
                email = "admin@exemple.com",
                password = "password"
            ),
            String::class.java
        )
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.headers["Set-Cookie"]).isNotEmpty
    }

    @Test
    fun `Login with wrong password should return a 400`() {
        val entity = restTemplate.postForEntity(
            URI("/auth/login"),
            LoginDto(
                email = "admin@exemple.com",
                password = "wrong password"
            ),
            String::class.java
        )
        assertThat(entity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `Login with wrong email should return a 404`() {
        val entity = restTemplate.postForEntity(
            URI("/auth/login"),
            LoginDto(
                email = "not-existing-email@exemple.com",
                password = "password"
            ),
            String::class.java
        )
        assertThat(entity.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    fun `Login with a customer should return a 403`() {
        val headers = restTemplate.loginAndGetHeaders()
        restTemplate.createCustomer(createCustomerDto().copy(email = "customer@exemple.com", password = "password"), headers)

        val entity = restTemplate.postForEntity(
            URI("/auth/login"),
            LoginDto(
                email = "customer@exemple.com",
                password = "password"
            ),
            Any::class.java
        )
        assertThat(entity.statusCode).isEqualTo(HttpStatus.FORBIDDEN)
    }
}