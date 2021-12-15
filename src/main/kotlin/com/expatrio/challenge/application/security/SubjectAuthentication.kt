package com.expatrio.challenge.application.security

import com.expatrio.challenge.domain.Subject
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class SubjectAuthentication(
    val subject: Subject
): Authentication {

    override fun getName(): String? {
        return null
    }

    override fun getAuthorities(): List<GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = mutableListOf()

        if (subject.role != null) {
            authorities.add(SimpleGrantedAuthority(subject.role))
        }

        return authorities
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getDetails(): Any? {
        return null
    }

    override fun getPrincipal(): String? {
        return subject.id
    }

    override fun isAuthenticated(): Boolean {
        return subject.authenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        throw RuntimeException()
    }
}