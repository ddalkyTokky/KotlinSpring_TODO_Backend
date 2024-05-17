package com.soonyong.todo.domain.todo.dto

import com.soonyong.todo.domain.todo.model.TodoStatus
import java.sql.Timestamp

data class TodoDetailResponse(
    val title: String?,
    val content: String?,
    val createdAt: Timestamp?,
    val author: String?,
    val status: TodoStatus,
    val replies: MutableList<String> = mutableListOf()
)