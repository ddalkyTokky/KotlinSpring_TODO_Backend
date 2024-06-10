package com.soonyong.todo.infra.exception

import org.springframework.security.core.AuthenticationException

class SignInFailException: AuthenticationException("Password is not correct!!")