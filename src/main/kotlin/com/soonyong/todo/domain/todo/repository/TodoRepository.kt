package com.soonyong.todo.domain.todo.repository

import com.soonyong.todo.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<Todo, Long> {
    fun findAllByOrderByCreatedAtAsc(): List<Todo>
    fun findAllByOrderByCreatedAtDesc(): List<Todo>

    fun findAllByMemberNameOrderByCreatedAtAsc(name: String?): List<Todo>
    fun findAllByMemberNameOrderByCreatedAtDesc(name: String?): List<Todo>
}