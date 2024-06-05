package com.soonyong.todo.domain.reply.controller

import com.soonyong.todo.domain.member.dto.MemberToken
import com.soonyong.todo.domain.member.service.MemberService
import com.soonyong.todo.domain.reply.dto.ReplyRequest
import com.soonyong.todo.domain.reply.service.ReplyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.soonyong.todo.domain.reply.dto.ReplyResponse
import com.soonyong.todo.infra.security.tokenParsing
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
        val token: String = httpsHeaders.get("Authorization")?.get(0) ?: throw
        memberService.tokenValidation(token)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                replyService.createReply(
                    todoId,
                    memberToken.memberId,
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
        val token: String = httpsHeaders.get("Authorization")?.get(0) ?: throw
        memberService.tokenValidation(token)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(replyService.updateReply(replyId, memberToken.memberId, replyRequest))
    }

    @DeleteMapping("/{replyId}")
    fun deleteReply(
        @PathVariable replyId: Long,
        @RequestHeader httpsHeaders: HttpHeaders
    ): ResponseEntity<Unit> {
        val token: String = httpsHeaders.get("Authorization")?.get(0) ?: throw
        memberService.tokenValidation(token)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(replyService.deleteReply(replyId, memberToken.memberId))
    }
}