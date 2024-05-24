package com.soonyong.todo.domain.reply.dto

import jakarta.validation.constraints.NotBlank

data class ReplyRequest (
    @field:NotBlank(message = "content cannot be blank")
    val content: String
)