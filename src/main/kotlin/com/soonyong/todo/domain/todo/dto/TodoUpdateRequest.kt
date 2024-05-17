package com.soonyong.todo.domain.todo.dto

data class TodoUpdateRequest (
    val name: String?,
    val title: String?,
    val content: String?
)