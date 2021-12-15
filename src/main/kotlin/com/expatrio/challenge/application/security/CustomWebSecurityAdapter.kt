package com.expatrio.challenge.application.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class CustomWebSecurityAdapter(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http {
            sessionManagement { SessionCreationPolicy.STATELESS }
            csrf { disable() }
            httpBasic { disable() }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtAuthenticationFilter)
        }
    }
}