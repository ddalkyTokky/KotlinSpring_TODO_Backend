package com.soonyong.todo.domain.reply.dto

import com.soonyong.todo.domain.todo.model.TodoStatus
import java.sql.Timestamp

data class ReplyResponse (
    val author: String?,
    val content: String?,
)