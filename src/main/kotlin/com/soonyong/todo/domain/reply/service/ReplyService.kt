package com.soonyong.todo.domain.reply.service

import com.soonyong.todo.domain.member.service.MemberService
import com.soonyong.todo.domain.reply.repository.ReplyRepository
import com.soonyong.todo.domain.reply.dto.ReplyRequest
import com.soonyong.todo.domain.reply.dto.ReplyResponse
import com.soonyong.todo.domain.reply.model.Reply
import com.soonyong.todo.domain.todo.service.TodoService
import com.soonyong.todo.infra.exception.ModelNotFoundException
import com.soonyong.todo.infra.exception.TokenException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReplyService (
    private val replyRepository : ReplyRepository,
    private val memberService : MemberService,
    private val todoService : TodoService
){
    @Transactional
    fun createReply(todoId: Long,
                    memberId: Long,
                    replyRequest: ReplyRequest
    ): ReplyResponse {
        return replyRepository.save(
            Reply.createReply(
                memberService.getMemberById(memberId),
                todoService.getTodoById(todoId),
                replyRequest
            )
        ).toResponse()
    }

    @Transactional
    fun deleteReply(replyId: Long, memberId: Long) {
        val reply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId.toString())
        if(memberId != reply.member!!.id) {
            throw TokenException("UnAuthorized Access Token")
        }
        replyRepository.delete(reply)
    }

    @Transactional
    fun updateReply(
        replyId: Long,
        memberId: Long,
        replyRequest: ReplyRequest
    ): ReplyResponse {
        val reply = replyRepository.findByIdOrNull(replyId) ?: throw ModelNotFoundException("Reply", replyId.toString())
        if(memberId != reply.member!!.id) {
            throw TokenException("UnAuthorized Access Token")
        }
        return reply.updateReply(replyRequest).toResponse()
    }
}