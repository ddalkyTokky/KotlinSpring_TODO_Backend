package com.soonyong.todo.domain.todo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class TodoCreateRequest (
    val member_id: Long, // TODO 로그인 기능 도입 후 삭제 예정

    @field:Size(max = 200, message = "title length should be shorter than 200")
    @field:NotBlank(message = "title cannot be blank")
    val title: String,

    @field:Size(max = 1000, message = "content length should be shorter than 1000")
    @field:NotBlank(message = "content cannot be blank")
    val content: String
)