package com.soonyong.todo.domain.reply.service

import com.soonyong.todo.domain.reply.repository.ReplyRepository
import org.springframework.stereotype.Service

@Service
class ReplyService (
    private val replyRepository : ReplyRepository
){

}