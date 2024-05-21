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

    @Size(max = 16, message = "name length should be shorter than 16")
    @NotBlank(message = "name cannot be Blank")
    @Column(unique = true, nullable = false, length = 16)
    var name: String? = null

    @Size(min = 8, max = 32, message = "password length should be 8 ~ 32")
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

    fun updateName(name: String){
        this.name = name
    }

    fun toResponse(): MemberResponse {
        return MemberResponse(
            name = this.name
        )
    }
}