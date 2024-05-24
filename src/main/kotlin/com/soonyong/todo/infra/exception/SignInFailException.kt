package com.soonyong.todo.infra.exception

import org.springframework.security.core.AuthenticationException

data class SignInFailException (
    val nameOrPw: String
): AuthenticationException("${nameOrPw} is not correct!!")