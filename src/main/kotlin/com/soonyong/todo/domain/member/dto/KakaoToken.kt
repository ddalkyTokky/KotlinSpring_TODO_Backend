package com.soonyong.todo.domain.member.dto

data class KakaoToken (
    val access_token: String,
    val token_type: String,
    val refresh_token: String,
    val expires_in: String,
    val scope: String,
    val refresh_token_expires_in: String
)