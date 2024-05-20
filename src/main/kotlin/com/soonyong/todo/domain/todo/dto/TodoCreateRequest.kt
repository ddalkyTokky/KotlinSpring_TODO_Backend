package com.soonyong.todo.domain.todo.dto

import com.soonyong.todo.domain.todo.model.TodoStatus

data class TodoCreateRequest (
    val member_id: Long, // TODO 로그인 기능 도입 후 삭제 예정
    val title: String,
    val content: String
)