package com.expatrio.challenge.application.security

import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val jwtEncoder: JwtEncoder
): GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (request is HttpServletRequest && response is HttpServletResponse) {
            val token: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
            val subject = jwtEncoder.decode(token)
            SecurityContextHolder.getContext().authentication = SubjectAuthentication(subject)
        }

        try {
            chain.doFilter(request, response)
        } finally {
            SecurityContextHolder.getContext().authentication = null // Clean authentication after process
        }
    }
}