package com.soonyong.todo.domain.member.contoller

import com.soonyong.todo.domain.member.dto.MemberRequest
import com.soonyong.todo.domain.member.dto.MemberResponse
import com.soonyong.todo.domain.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@RequestMapping("member")
@RestController
class MemberController (
    private val memberService: MemberService
){
    private val restClient: RestClient = RestClient.create()

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
    fun redirect(@RequestParam requestParam: Map<String, String>): String{
        val requestData = mutableMapOf(
            "grant_type" to "authorization_code",
            "client_id" to "SoonYong",
            "code" to requestParam.get("code")
        )
        return restClient.post()
            .uri("https://kauth.kakao.com/oauth/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { this.setAll(requestData) })
            .retrieve()
            .onStatus(HttpStatusCode::isError) { _, _ ->
                throw RuntimeException("카카오 AccessToken 조회 실패")
            }
            .body<String>()
            ?: throw RuntimeException("카카오 AccessToken 조회 실패")
    }
}