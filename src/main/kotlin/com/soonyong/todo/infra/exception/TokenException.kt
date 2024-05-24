package com.soonyong.todo.infra.exception

import org.springframework.security.core.AuthenticationException
import java.sql.Timestamp

class TokenException(
    val messege: String
): AuthenticationException(messege)