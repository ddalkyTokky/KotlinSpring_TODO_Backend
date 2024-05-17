package com.soonyong.todo.domain.todo.repository

import com.soonyong.todo.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<Todo, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<Todo>
    fun findAllByOrderByCreatedAt(): List<Todo>
}