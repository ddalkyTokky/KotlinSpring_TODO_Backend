package com.soonyong.todo.domain.member.contoller

import com.soonyong.todo.domain.member.dto.MemberRequest
import com.soonyong.todo.domain.member.dto.MemberResponse
import com.soonyong.todo.domain.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("member")
@RestController
class MemberController (
    private val memberService: MemberService
){
    @PostMapping("new")
    fun createMember(@RequestBody @Valid memberRequest: MemberRequest): ResponseEntity<MemberResponse>{
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(memberService.createMember(memberRequest))
    }

    @PostMapping("signin")
    fun signin(@RequestBody @Valid memberRequest: MemberRequest): ResponseEntity<String>{
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(memberService.signin(memberRequest))
    }

    @GetMapping("redirect")
    fun redirect(@RequestParam requestParam: Map<String, String>): ResponseEntity<String>{
        return ResponseEntity
            .status(HttpStatus.FOUND)
            .body(requestParam.get("code"))
    }
}