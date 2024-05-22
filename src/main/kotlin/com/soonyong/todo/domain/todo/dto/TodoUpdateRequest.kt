package com.soonyong.todo.domain.todo.dto

import jakarta.validation.constraints.Size

data class TodoUpdateRequest (
    @field:Size(max = 200, message = "title length should be shorter than 200")
    val title: String?,

    @field:Size(max = 1000, message = "content length should be shorter than 1000")
    val content: String?
)