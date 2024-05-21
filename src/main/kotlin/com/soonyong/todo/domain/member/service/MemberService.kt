package com.soonyong.todo.domain.member.service

import com.soonyong.todo.domain.member.dto.MemberRequest
import com.soonyong.todo.domain.member.dto.MemberResponse
import com.soonyong.todo.domain.member.model.Member
import com.soonyong.todo.domain.member.repository.MemberRepository
import com.soonyong.todo.domain.reply.dto.ReplyRequest
import com.soonyong.todo.domain.reply.dto.ReplyResponse
import com.soonyong.todo.domain.reply.model.Reply
import com.soonyong.todo.infra.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService (
    private val memberRepository: MemberRepository
){
    fun getMemberById(memberId: Long): Member {
        val member: Member = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId.toString())
        return member
    }

    @Transactional
    fun createMember(memberReqeust: MemberRequest): MemberResponse {
        return memberRepository.save(
            Member.createMember(
                memberReqeust
            )
        ).toResponse()
    }
}