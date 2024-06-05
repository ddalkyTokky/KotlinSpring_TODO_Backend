package com.soonyong.todo.domain.member.service

import com.soonyong.todo.domain.member.dto.MemberRequest
import com.soonyong.todo.domain.member.dto.MemberResponse
import com.soonyong.todo.domain.member.dto.MemberToken
import com.soonyong.todo.domain.member.model.Member
import com.soonyong.todo.domain.member.repository.MemberRepository
import com.soonyong.todo.infra.exception.ModelNotFoundException
import com.soonyong.todo.infra.exception.SignInFailException
import com.soonyong.todo.infra.security.service.JwtService
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService (
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtService: JwtService
) {
    fun getMemberById(memberId: Long): Member {
        val member: Member =
            memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException("Member", memberId.toString())
        return member
    }

    @Transactional
    fun createMember(memberRequest: MemberRequest): MemberResponse {
        val secret: String = RandomStringUtils.randomAlphabetic(256)
        return memberRepository.save(
            Member.createMember(
                memberRequest.name,
                bCryptPasswordEncoder.encode(memberRequest.pw),
                secret
            )
        ).toResponse()
    }

    fun signin(memberRequest: MemberRequest): MemberToken {
        val member: Member =
            memberRepository.findMemberByName(memberRequest.name)
                ?: throw ModelNotFoundException("Member", memberRequest.name)

        if (bCryptPasswordEncoder.matches(memberRequest.pw, member.pw)) {
            return MemberToken(
                member.id!!,
                jwtService.generateToken("accessToken", member.name!!, member.secret!!)
            )
        }
        throw SignInFailException()
    }

    fun tokenValidation(memberToken: MemberToken) {
        val member: Member =
            memberRepository.findByIdOrNull(memberToken.memberId)
                ?: throw ModelNotFoundException("Member", memberToken.memberId.toString())
        jwtService.validateToken(memberToken.token, member.secret!!)
    }
}