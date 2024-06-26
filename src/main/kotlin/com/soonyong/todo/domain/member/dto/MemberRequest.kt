package com.soonyong.todo.domain.member.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class MemberRequest(
    @field:Size(max = 16, message = "name length should be shorter than 16")
    @field:NotBlank(message = "name cannot be Blank")
    val name: String,

    @field:Size(min = 8, max = 16, message = "password length should be 8 ~ 16")
    @field:NotBlank(message = "pw cannot be Blank")
    val pw: String
)