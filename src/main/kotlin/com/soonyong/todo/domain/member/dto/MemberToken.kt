package com.soonyong.todo.domain.member.dto

import java.time.LocalDateTime

data class MemberToken (
    val memberId: Long,
    val token: String
)