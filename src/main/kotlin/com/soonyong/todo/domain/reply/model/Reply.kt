package com.soonyong.todo.domain.reply.model;

import jakarta.persistence.*

@Entity
class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
