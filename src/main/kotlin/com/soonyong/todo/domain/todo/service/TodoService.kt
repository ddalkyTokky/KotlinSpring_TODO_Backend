package com.soonyong.todo.domain.todo.service;

import com.soonyong.todo.domain.member.service.MemberService
import com.soonyong.todo.domain.todo.dto.TodoCreateRequest
import com.soonyong.todo.domain.todo.dto.TodoResponse
import com.soonyong.todo.domain.todo.dto.TodoUpdateRequest
import com.soonyong.todo.domain.todo.model.Todo
import com.soonyong.todo.domain.todo.repository.TodoRepository
import com.soonyong.todo.infra.exception.ModelNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional

@Service
public class TodoService (
    private val todoRepository: TodoRepository,
    private val memberService : MemberService
) {
    fun getTodoResponseById(todoId: Long): TodoResponse {
        val todo: Todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        return todo.toResponse()
    }

    @Transactional
    fun createTodo(todoCreateRequest: TodoCreateRequest): TodoResponse {
        return todoRepository.save(
            Todo.createTodo(
                todoCreateRequest,
                memberService.getMemberById(todoCreateRequest.member_id)
            )
        ).toResponse()
    }

    fun getAllTodoList(orderBy: String): List<TodoResponse> {
        if (orderBy.equals("descend")) {
            return todoRepository.findAllByOrderByCreatedAtDesc().map { it.toResponse() }
        }
        return todoRepository.findAllByOrderByCreatedAt().map { it.toResponse() }
    }

    @Transactional
    fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        todoRepository.delete(todo)
    }

    @Transactional
    fun finishTodo(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        return todo.finishTodo().toResponse()
    }

    @Transactional
    fun updateTodo(todoId: Long, todoUpdateRequest: TodoUpdateRequest): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        return todo.updateTodo(todoUpdateRequest).toResponse()
    }
}