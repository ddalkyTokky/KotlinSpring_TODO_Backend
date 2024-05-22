package com.soonyong.todo.domain.todo.dto

data class TodoSearch (
    val order: String?,
    val member: String?,
    val page: Long?,
    val pageSize: Long?
)