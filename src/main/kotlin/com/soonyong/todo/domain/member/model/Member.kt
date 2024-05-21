package com.soonyong.todo.domain.member.model

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

    @NotBlank(message = "name cannot be Blank")
    @Column(unique = true, nullable = false, length = 16)
    var name: String? = null

    @Size(min = 8, message = "password should be longger than 8")
    @Column(nullable = false, length = 32)
    var pw: String? = null

    companion object {
        fun createMember(name: String, pw: String): Member {
            val member: Member = Member()
            member.name = name
            member.pw = pw
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