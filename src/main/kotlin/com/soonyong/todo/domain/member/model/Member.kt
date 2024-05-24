package com.soonyong.todo.domain.member.model

import com.soonyong.todo.domain.CreatedAtEntity
import com.soonyong.todo.domain.member.dto.MemberRequest
import com.soonyong.todo.domain.member.dto.MemberResponse
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lombok.Getter

@Entity
@Getter
class Member: CreatedAtEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true, nullable = false, length = 16)
    var name: String? = null

    @Column(nullable = false, length = 64)
    var pw: String? = null

    companion object {
        fun createMember(name: String, pw: String): Member {
            val member: Member = Member()
            member.name = name
            member.pw = pw
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