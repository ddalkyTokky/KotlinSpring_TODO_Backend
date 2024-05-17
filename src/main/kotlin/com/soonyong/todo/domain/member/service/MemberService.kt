package com.soonyong.todo.domain.member.service

import com.soonyong.todo.domain.member.dto.MemberResponse
import com.soonyong.todo.domain.member.model.Member
import com.soonyong.todo.domain.member.repository.MemberRepository
import com.soonyong.todo.domain.todo.dto.TodoResponse
import com.soonyong.todo.infra.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService (
    private val memberRepository: MemberRepository
){
    fun getMemberById(memberId: Long): Member {
        val member: Member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Todo", memberId)
        return member
    }
}