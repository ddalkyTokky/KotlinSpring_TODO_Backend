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

    @Column(nullable = false, length = 200)
    var title: String? = null

    @Column(nullable = false, length = 1000)
    var content: String? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TodoStatus = TodoStatus.WORKING

    @OneToMany(mappedBy = "todo", fetch = FetchType.LAZY, orphanRemoval = true)
    val replies: MutableList<Reply> = mutableListOf()

    companion object{
        fun createTodo(
            todoRequest: TodoCreateRequest,
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

    fun updateTodo(todoUpdateRequest: TodoUpdateRequest): Todo{
        if(todoUpdateRequest.title?.isBlank() == false){
            this.title = todoUpdateRequest.title
        }
        if(todoUpdateRequest.content?.isBlank() == false) {
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
