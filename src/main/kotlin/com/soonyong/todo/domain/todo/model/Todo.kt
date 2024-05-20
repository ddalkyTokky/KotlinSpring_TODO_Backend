package com.soonyong.todo.domain.todo.model

import com.soonyong.todo.domain.CreatedAtEntity
import com.soonyong.todo.domain.member.model.Member
import com.soonyong.todo.domain.reply.model.Reply
import com.soonyong.todo.domain.todo.dto.TodoCreateRequest
import com.soonyong.todo.domain.todo.dto.TodoDetailResponse
import com.soonyong.todo.domain.todo.dto.TodoSimpleResponse
import com.soonyong.todo.domain.todo.dto.TodoUpdateRequest
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

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, orphanRemoval = true)
    val replies: MutableList<Reply> = mutableListOf()

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

    fun finishTodo(): Todo {
        this.status = TodoStatus.DONE
        return this
    }

    fun updateTodo(todoUpdateRequest: TodoUpdateRequest): Todo{
        if(todoUpdateRequest.title != null){
            this.title = todoUpdateRequest.title
        }
        if(todoUpdateRequest.content != null) {
            this.content = todoUpdateRequest.content
        }
        return this
    }

    fun toSimpleResponse(): TodoSimpleResponse {
        return TodoSimpleResponse(
            title = this.title,
            content = this.content,
            createdAt = this.createdAt,
            author = this.member?.name,
            status = this.status
        )
    }

    fun toDetailResponse(): TodoDetailResponse {
        return TodoDetailResponse(
            title = this.title,
            content = this.content,
            createdAt = this.createdAt,
            author = this.member?.name,
            status = this.status,
            replies = this.replies.map {it.toResponse()}
        )
    }
}
