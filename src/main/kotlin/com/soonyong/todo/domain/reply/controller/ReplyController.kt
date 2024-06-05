package com.soonyong.todo.domain.reply.controller

import com.soonyong.todo.domain.member.service.MemberService
import com.soonyong.todo.domain.reply.dto.ReplyRequest
import com.soonyong.todo.domain.reply.service.ReplyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.soonyong.todo.domain.reply.dto.ReplyResponse
import com.soonyong.todo.infra.exception.TokenException
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders

@RequestMapping("/reply")
@RestController
class ReplyController (
    private val replyService: ReplyService,
    private val memberService: MemberService
) {
    @PostMapping("{todoId}")
    fun createReply(
        @PathVariable todoId: Long,
        @RequestBody @Valid replyRequest: ReplyRequest,
        @RequestHeader httpsHeaders: HttpHeaders
    ): ResponseEntity<ReplyResponse> {
        val token: String = httpsHeaders.get("Authorization")?.get(0) ?: throw TokenException("No Token Found")
        memberService.tokenValidation(token)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                replyService.createReply(
                    todoId,
                    1L,
                    replyRequest
                )
            )
    }

    @PutMapping("/{replyId}")
    fun updateReply(
        @PathVariable replyId: Long,
        @RequestBody @Valid replyRequest: ReplyRequest,
        @RequestHeader httpsHeaders: HttpHeaders
    ): ResponseEntity<ReplyResponse> {
        val token: String = httpsHeaders.get("Authorization")?.get(0) ?: throw TokenException("No Token Found")
        memberService.tokenValidation(token)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(replyService.updateReply(replyId, 1L, replyRequest))
    }

    @DeleteMapping("/{replyId}")
    fun deleteReply(
        @PathVariable replyId: Long,
        @RequestHeader httpsHeaders: HttpHeaders
    ): ResponseEntity<Unit> {
        val token: String = httpsHeaders.get("Authorization")?.get(0) ?: throw TokenException("No Token Found")
        memberService.tokenValidation(token)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(replyService.deleteReply(replyId, 1L))
    }
}