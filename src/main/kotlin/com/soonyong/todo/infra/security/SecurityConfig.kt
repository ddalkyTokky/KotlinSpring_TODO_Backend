package com.soonyong.todo.infra.security

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig (
    private val tokenProvider: TokenProvider,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
){
    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity.csrf {
            csrf -> csrf.disable()
        }

        httpSecurity.exceptionHandling {
            it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            it.accessDeniedHandler(jwtAccessDeniedHandler)
        }


        httpSecurity.headers {
            it.frameOptions {
                it.sameOrigin()
            }
        }


        httpSecurity.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        httpSecurity.authorizeHttpRequests {
            it.requestMatchers("/member/signin").permitAll() // 로그인 api
            it.requestMatchers("/member/new").permitAll() // 회원가입 api
            it.anyRequest().authenticated()
        }

//            .and()
//            .apply<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>>(JwtSecurityConfig(tokenProvider)) // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용

        return httpSecurity.build()
    }
}