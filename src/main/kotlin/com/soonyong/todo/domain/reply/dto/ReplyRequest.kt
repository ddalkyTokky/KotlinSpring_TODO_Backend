package com.soonyong.todo.domain.reply.dto

data class ReplyRequest (
    val member_id: Long,
    val content: String?
)