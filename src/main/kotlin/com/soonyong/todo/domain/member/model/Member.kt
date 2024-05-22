package com.soonyong.todo.domain.member.model

import com.soonyong.todo.domain.member.dto.MemberRequest
import com.soonyong.todo.domain.member.dto.MemberResponse
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lombok.Getter

@Entity
@Getter
class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true, nullable = false, length = 16)
    var name: String? = null

    @Column(nullable = false, length = 32)
    var pw: String? = null

    companion object {
        fun createMember(memberRequest: MemberRequest): Member {
            val member: Member = Member()
            member.name = memberRequest.name
            member.pw = memberRequest.pw
            return member
        }
    }

    fun updateMember(memberRequest: MemberRequest){
        this.name = memberRequest.name
        this.pw = memberRequest.pw
    }

    fun toResponse(): MemberResponse {
        return MemberResponse(
            name = this.name
        )
    }
}