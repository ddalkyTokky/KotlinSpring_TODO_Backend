package com.soonyong.todo.domain.reply.model;

import com.soonyong.todo.domain.member.model.Member
import com.soonyong.todo.domain.reply.dto.ReplyRequest
import com.soonyong.todo.domain.reply.dto.ReplyResponse
import com.soonyong.todo.domain.todo.model.Todo
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

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
        fun createReply(member: Member, todo: Todo, replyRequest: ReplyRequest): Reply {
            val reply: Reply = Reply()
            reply.member = member
            reply.todo = todo
            reply.content = replyRequest.content
            return reply
        }
    }

    fun updateReply(replyRequest: ReplyRequest): Reply{
        this.content = replyRequest.content
        return this
    }

    fun toResponse(): ReplyResponse {
        return ReplyResponse(
            this.todo?.title,
            this.member?.name,
            this.content
        )
    }
}