package com.soonyong.todo.domain.todo.model

import jakarta.persistence.*

@Entity
class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
