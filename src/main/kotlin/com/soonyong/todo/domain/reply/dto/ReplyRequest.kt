package com.soonyong.todo.domain.reply.dto

import jakarta.validation.constraints.NotBlank

data class ReplyRequest (
    val member_id: Long, // TODO 로그인 기능 도입 후 삭제 예정.

    @field:NotBlank(message = "content cannot be blank")
    val content: String
)