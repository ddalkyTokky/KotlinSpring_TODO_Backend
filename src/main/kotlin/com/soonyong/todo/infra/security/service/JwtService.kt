package com.soonyong.todo.infra.security.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.Date

@Service
class JwtService {
    companion object{
        const val ISSUER = "team.sparta.com"
        const val ACCESS_TOKEN_EXPIRATION_HOUR : Long = 168
    }

    fun validateToken(token: String, secret: String) : Result<Jws<Claims>>{
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
        }
    }

    fun generateToken(subject: String, memberName: String, secret: String): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("memberName" to memberName))
            .build()

        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer(ISSUER)
            .issuedAt(Date.from(now))
            .expiration(
                Date.from(
                    now.plus(
                        Duration.ofHours(ACCESS_TOKEN_EXPIRATION_HOUR)
                    )
                )
            )
            .claims(claims)
            .signWith(key)
            .compact()
    }
}