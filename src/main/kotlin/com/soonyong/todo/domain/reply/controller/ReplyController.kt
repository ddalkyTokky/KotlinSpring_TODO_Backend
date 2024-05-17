package com.soonyong.todo.domain.reply.controller

import com.soonyong.todo.domain.reply.service.ReplyService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/reply")
@RestController
class ReplyController (
    private val replyService: ReplyService
){
}