package com.soonyong.todo.domain.member.model

import com.soonyong.todo.domain.todo.model.Todo
import jakarta.persistence.*
import lombok.Getter

@Entity
@Getter
class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true, nullable = false, length = 16)
    val name: String? = null

    @Column(nullable = false, length = 32)
    val pw: String? = null
}