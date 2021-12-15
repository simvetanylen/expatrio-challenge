package com.expatrio.challenge.application.rest

import com.expatrio.challenge.application.security.JwtEncoder
import com.expatrio.challenge.domain.Role
import com.expatrio.challenge.domain.Subject
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("auth")
class AuthenticationWebService(
    @Value("\${jwt.durationInMinutes}") tokenDurationInMinutes: Long,
    private val jwtEncoder: JwtEncoder,
) {

    private val maxAge: Long = tokenDurationInMinutes * 60

    @PostMapping("login")
    fun login(@RequestBody dto: LoginDto): ResponseEntity<Void> {
        val subject = Subject(
            id = UUID.randomUUID().toString(),
            role = Role.ADMIN,
            authenticated = true
        )

        val token = jwtEncoder.encode(subject)

        return ResponseEntity.ok()
            .header("Set-Cookie", "Authorization=Bearer $token; Max-Age=${maxAge}; Path=/; HttpOnly")
            .build()
    }

    @PostMapping("logout")
    fun logout() : ResponseEntity<Void> {
        return ResponseEntity.ok()
            .header("Set-Cookie", "Authorization=; Max-Age=0; Path=/; HttpOnly")
            .build()
    }
}