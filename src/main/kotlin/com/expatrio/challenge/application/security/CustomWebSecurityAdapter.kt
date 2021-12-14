package com.expatrio.challenge.application.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke

@Configuration
class CustomWebSecurityAdapter : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http {
            sessionManagement { SessionCreationPolicy.STATELESS }
            csrf { disable() }
            httpBasic { disable() }
        }
    }
}