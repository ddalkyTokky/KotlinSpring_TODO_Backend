package com.soonyong.todo.domain.reply.controller

import com.soonyong.todo.domain.reply.dto.ReplyResponse
import com.soonyong.todo.domain.reply.service.ReplyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/reply")
@RestController
class ReplyController (
    private val replyService: ReplyService
){
}