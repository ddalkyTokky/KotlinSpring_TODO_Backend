package com.soonyong.todo.domain.todo.model

import com.soonyong.todo.domain.CreatedAtEntity
import com.soonyong.todo.domain.member.model.Member
import com.soonyong.todo.domain.reply.model.Reply
import com.soonyong.todo.domain.todo.dto.TodoRequest
import com.soonyong.todo.domain.todo.dto.TodoDetailResponse
import com.soonyong.todo.domain.todo.dto.TodoSimpleResponse
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
            todoRequest: TodoRequest,
            member: Member
            ): Todo {
            val todo: Todo = Todo()
            todo.member = member
            todo.title = todoRequest.title
            todo.content = todoRequest.content
            todo.status = TodoStatus.WORKING
            return todo
        }
    }

    fun finishTodo(): Todo {
        this.status = TodoStatus.DONE
        return this
    }

    fun updateTodo(todoRequest: TodoRequest): Todo{
        if(todoRequest.title != null){
            this.title = todoRequest.title
        }
        if(todoRequest.content != null) {
            this.content = todoRequest.content
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
