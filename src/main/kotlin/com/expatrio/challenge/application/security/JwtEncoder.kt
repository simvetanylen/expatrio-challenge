package com.expatrio.challenge.application.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.expatrio.challenge.domain.Subject
import com.expatrio.challenge.domain.exception.UnauthorizedException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.*

@Component
class JwtEncoder(
    @Value("\${jwt.secretKey}") private val secretKey: String,
    @Value("\${jwt.issuer}") private val issuer: String,
    @Value("\${jwt.durationInMinutes}") private val durationInMinutes: Long
) {

    private val algorithm = Algorithm.HMAC512(secretKey)
    private val verifier = JWT.require(algorithm).withIssuer(issuer).build()

    companion object {
        private val LOGGER = LoggerFactory.getLogger(JwtEncoder::class.java)

        internal const val AUTHENTICATED_LABEL = "authenticated"
        internal const val ID_LABEL = "id"
        internal const val ROLE_LABEL = "role"
    }

    fun decode(token: String?): Subject {
        if (token == null) {
            return Subject.unauthenticated()
        }

        try {
            val decodedToken = verifier.verify(token.removePrefix("Bearer "))

            val id = decodedToken.getClaim(ID_LABEL)?.asString()
            val authenticated = decodedToken.getClaim(AUTHENTICATED_LABEL)?.asBoolean() ?: false
            val role = decodedToken.getClaim(ROLE_LABEL)?.asString()

            return Subject(
                authenticated = authenticated,
                id = UUID.fromString(id),
                role = role
            )
        } catch (t: Throwable) {
            LOGGER.error(t.message, t)
            throw UnauthorizedException()
        }
    }

    fun encode(subject: Subject): String {
        return JWT.create()
            .withIssuer(issuer)
            .withExpiresAt(
                Date.from(
                    LocalDateTime
                        .now()
                        .plusMinutes(durationInMinutes)
                        .toInstant(OffsetDateTime.now().offset)
                )
            )
            .withClaim(AUTHENTICATED_LABEL, subject.authenticated)
            .withClaim(ID_LABEL, subject.id?.toString())
            .withClaim(ROLE_LABEL, subject.role)
            .sign(algorithm)
    }
}