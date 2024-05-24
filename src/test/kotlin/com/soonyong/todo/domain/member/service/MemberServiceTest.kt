package com.soonyong.todo.domain.member.service

import com.soonyong.todo.domain.member.dto.MemberRequest
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
    fun memberServiceTest() {
        memberService.createMember(
            MemberRequest(
                "name",
                "password"
            )
        )
    }
}