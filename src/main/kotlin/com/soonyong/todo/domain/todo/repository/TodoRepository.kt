package com.soonyong.todo.domain.todo.repository

import com.soonyong.todo.domain.todo.model.Todo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<Todo, Long> {
    fun findAllByOrderByCreatedAtAsc(pageable: Pageable): Page<Todo>
    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<Todo>

    fun findAllByMemberNameOrderByCreatedAtAsc(name: String?, pageable: Pageable): Page<Todo>
    fun findAllByMemberNameOrderByCreatedAtDesc(name: String?, pageable: Pageable): Page<Todo>

//    @Query("SELECT t FROM Todo t join fetch t.replies")
//    fun findAllWithRepliesUsingFetchJoin(): List<Todo>
}