package com.soonyong.todo.domain.todo.service;

import com.soonyong.todo.domain.todo.dto.TodoResponse
import com.soonyong.todo.domain.todo.model.Todo
import com.soonyong.todo.domain.todo.model.TodoStatus
import com.soonyong.todo.domain.todo.repository.TodoRepository
import com.soonyong.todo.infra.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service;
import java.sql.Timestamp

@Service
public class TodoService (
    private val todoRepository: TodoRepository
){
    fun getTodoById(todoId: Long): TodoResponse {
        val todo: Todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId)
        return todo.toResponse()
    }


}
