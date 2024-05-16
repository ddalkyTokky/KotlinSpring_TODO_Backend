package com.soonyong.todo.domain.todo.model

import com.soonyong.todo.domain.CreatedAtEntity
import com.soonyong.todo.domain.member.model.Member
import jakarta.persistence.*
import lombok.Getter

@Entity
@Getter
class Todo(): CreatedAtEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null

    @Column(nullable = false, length = 32)
    var title: String? = null

    @Column(nullable = false)
    var content: String? = null

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TodoStatus = TodoStatus.WORKING

    fun createTodo(member: Member, title: String, content: String): Todo{
        val todo: Todo = Todo()
        todo.member = member
        todo.title = title
        todo.content = content
        todo.status = TodoStatus.WORKING
        return todo
    }
}
