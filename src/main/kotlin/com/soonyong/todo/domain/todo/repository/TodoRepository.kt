package com.soonyong.todo.domain.todo.repository

import com.soonyong.todo.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TodoRepository: JpaRepository<Todo, Long> {
    fun findAllByOrderByCreatedAtAsc(): List<Todo>
    fun findAllByOrderByCreatedAtDesc(): List<Todo>

    fun findAllByMemberNameOrderByCreatedAtAsc(name: String?): List<Todo>
    fun findAllByMemberNameOrderByCreatedAtDesc(name: String?): List<Todo>

//    @Query("SELECT t FROM Todo t join fetch t.replies")
//    fun findAllWithRepliesUsingFetchJoin(): List<Todo>
}