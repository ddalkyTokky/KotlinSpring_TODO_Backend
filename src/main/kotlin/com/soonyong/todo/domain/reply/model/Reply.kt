package com.soonyong.todo.domain.reply.model;

import com.soonyong.todo.domain.member.model.Member
import com.soonyong.todo.domain.todo.model.Todo
import jakarta.persistence.*

@Entity
class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    val todo: Todo? = null

    @Column(nullable = false)
    var content: String? = null
}
