package com.soonyong.todo.domain.member.service

import com.soonyong.todo.domain.member.dto.MemberRequest
import com.soonyong.todo.domain.member.dto.MemberResponse
import com.soonyong.todo.domain.member.dto.MemberToken
import com.soonyong.todo.domain.member.model.Member
import com.soonyong.todo.domain.member.repository.MemberRepository
import com.soonyong.todo.infra.exception.ModelNotFoundException
import com.soonyong.todo.infra.exception.SignInFailException
import com.soonyong.todo.infra.exception.TokenException
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MemberService (
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtConfig
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
                sha256(member.id!!.toString() + member.pw + expireAt.toString()),
                expireAt
            )
        }
        throw SignInFailException()
    }

    fun tokenValidation(memberToken: MemberToken) {
        if (memberToken.expireAt.isBefore(LocalDateTime.now())) {
            throw TokenException("token expiredAt ${memberToken.expireAt}")
        }

        val member: Member =
            memberRepository.findByIdOrNull(memberToken.memberId)
                ?: throw TokenException("Unvaild Token memberId")

        if(sha256(memberToken.memberId.toString() + member.pw + memberToken.expireAt) != memberToken.token){
            throw TokenException("Unvaild Token!!")
        }
    }
}