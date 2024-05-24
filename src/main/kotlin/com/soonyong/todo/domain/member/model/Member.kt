package com.soonyong.todo.domain.member.model

import com.soonyong.todo.domain.CreatedAtEntity
import com.soonyong.todo.domain.member.dto.MemberRequest
import com.soonyong.todo.domain.member.dto.MemberResponse
import jakarta.persistence.*
import lombok.Getter

@Entity
@Getter
class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true, nullable = false, length = 16)
    var name: String? = null

    @Column(nullable = false, length = 64)
    var pw: String? = null

    @Column(nullable = false, length = 8)
    var secret: String? = null

    companion object {
        fun createMember(name: String, pw: String, secret: String): Member {
            val member: Member = Member()
            member.name = name
            member.pw = pw
            member.secret = secret
            return member
        }
    }

    fun toResponse(): MemberResponse {
        return MemberResponse(
            name = this.name
        )
    }
}