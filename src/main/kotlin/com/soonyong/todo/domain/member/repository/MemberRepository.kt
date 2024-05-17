package com.soonyong.todo.domain.member.repository

import com.soonyong.todo.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
}