package com.soonyong.todo.domain.reply.model;

import com.soonyong.todo.domain.member.model.Member
import com.soonyong.todo.domain.reply.dto.ReplyResponse
import com.soonyong.todo.domain.todo.model.Todo
import com.soonyong.todo.domain.todo.model.TodoStatus
import jakarta.persistence.*

@Entity
class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    var todo: Todo? = null

    @Column(nullable = false)
    var content: String? = null

    companion object {
        fun createReply(member: Member, todo: Todo, content: String): Reply {
            val reply: Reply = Reply()
            reply.member = member
            reply.todo = todo
            reply.content = content
            return reply
        }
    }

    fun toResponse(): ReplyResponse {
        return ReplyResponse(
            this.member?.name,
            this.content
        )
    }
}