package com.soonyong.todo.domain.todo.service

import com.soonyong.todo.domain.todo.dto.TodoRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class TodoServiceTest() {
    @Autowired
    private lateinit var todoService: TodoService

    @Test
    @Rollback(false)
    fun todoMaker(){
        for (i in 1..100) {
            todoService.createTodo(
                TodoRequest(
                    1L,
                    "No.${i} title",
                    "No.${i} content"
                )
            )
        }
    }
}