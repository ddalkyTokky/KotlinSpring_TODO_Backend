package com.soonyong.todo.domain.member.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class MemberServiceTest () {
    @Autowired
    private lateinit var memberService: MemberService

    @Test
    @Rollback(false)
    fun memberPasswordLengthTest() {
        memberService.
            Member.createMember(
                "Lee Soon Yong",
                "he"
            )
        )
    }
}