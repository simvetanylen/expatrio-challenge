package com.expatrio.challenge

import com.expatrio.challenge.domain.Role
import com.expatrio.challenge.domain.User
import com.expatrio.challenge.domain.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*

@SpringBootApplication
@EnableTransactionManagement
@EnableGlobalMethodSecurity(securedEnabled = true)
class App(
    private val userRepository: UserRepository
): CommandLineRunner {

    override fun run(vararg args: String?) {
        // Create a base admin if none exist.
        val adminCount = userRepository.findAllByRole(Role.ADMIN).size

        if (adminCount == 0) {
            userRepository.create(User(
                id = UUID.randomUUID().toString(),
                firstname = "test",
                lastname = "test",
                role = Role.ADMIN,
                email = "admin@exemple.com",
                password = BCryptPasswordEncoder().encode("password"),
                description = null
            ))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
