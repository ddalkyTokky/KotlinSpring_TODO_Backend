package com.soonyong.todo.domain.reply.controller

import com.soonyong.todo.domain.reply.dto.ReplyRequest
import com.soonyong.todo.domain.reply.service.ReplyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.soonyong.todo.domain.reply.dto.ReplyResponse

@RequestMapping("/reply")
@RestController
class ReplyController (
    private val replyService: ReplyService
){
    @PostMapping("{todoId}")
    fun createReply(@PathVariable todoId: Long, @RequestBody replyRequest: ReplyRequest): ResponseEntity<ReplyResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(replyService.createReply(todoId, replyRequest))
    }

    @PutMapping("/{replyId}")
    fun updateReply(
        @PathVariable replyId: Long,
        replyRequest: ReplyRequest
    ): ResponseEntity<ReplyResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(replyService.updateReply(replyId, replyRequest))
    }

    @DeleteMapping("/{replyId}")
    fun deleteReply(
        @PathVariable replyId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(replyService.deleteReply(replyId))
    }
}