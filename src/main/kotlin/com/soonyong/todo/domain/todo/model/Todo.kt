package com.soonyong.todo.domain.todo.model

import com.soonyong.todo.domain.CreatedAtEntity
import com.soonyong.todo.domain.member.model.Member
import com.soonyong.todo.domain.todo.dto.TodoCreateRequest
import com.soonyong.todo.domain.todo.dto.TodoResponse
import jakarta.persistence.*
import lombok.Getter

@Entity
@Getter
class Todo(): CreatedAtEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null

    @Column(nullable = false, length = 32)
    var title: String? = null

    @Column(nullable = false)
    var content: String? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TodoStatus = TodoStatus.WORKING

    companion object{
        fun createTodo(
            todoCreateRequest: TodoCreateRequest,
            member: Member
            ): Todo {
            val todo: Todo = Todo()
            todo.member = member
            todo.title = todoCreateRequest.title
            todo.content = todoCreateRequest.content
            todo.status = TodoStatus.WORKING
            return todo
        }
    }

    fun toResponse(): TodoResponse {
        return TodoResponse(
            title = this.title,
            content = this.content,
            createdAt = this.createdAt,
            author = this.member?.name,
            status = this.status
        )
    }
}
