package com.soonyong.todo.domain.todo.dto

import com.soonyong.todo.domain.todo.model.TodoStatus

data class TodoCreateRequest (
    val member_id: Long,
    val title: String,
    val content: String,
    val status: TodoStatus
)