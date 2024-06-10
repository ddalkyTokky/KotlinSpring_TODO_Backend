package com.soonyong.todo.infra.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class BcryptConfig {
    @Bean
    fun bcryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}