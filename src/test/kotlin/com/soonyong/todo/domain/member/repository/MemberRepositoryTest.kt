package com.soonyong.todo.domain.member.repository

import com.soonyong.todo.domain.member.dto.MemberRequest
import com.soonyong.todo.domain.member.model.Member
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class MemberRepositoryTest () {
    @Autowired
    private lateinit var memberRepository : MemberRepository

    @Test
    @Rollback(false)
    fun memberCreateTest() {
        memberRepository.save(
            Member.createMember(
                MemberRequest(
                "Lee Soon Yong",
                "helloworld"
                )
            )
        )
    }
}