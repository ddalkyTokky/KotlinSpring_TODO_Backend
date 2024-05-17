package com.soonyong.todo.domain.todo.service;

import com.soonyong.todo.domain.member.service.MemberService
import com.soonyong.todo.domain.todo.dto.TodoCreateRequest
import com.soonyong.todo.domain.todo.dto.TodoDetailResponse
import com.soonyong.todo.domain.todo.dto.TodoSimpleResponse
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
    fun getTodoById(todoId: Long): Todo {
        val todo: Todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        return todo
    }

    @Transactional
    fun createTodo(todoCreateRequest: TodoCreateRequest): TodoSimpleResponse {
        return todoRepository.save(
            Todo.createTodo(
                todoCreateRequest,
                memberService.getMemberById(todoCreateRequest.member_id)
            )
        ).toSimpleResponse()
    }

    fun getAllTodoList(orderBy: String): List<TodoSimpleResponse> {
        if (orderBy.equals("descend")) {
            return todoRepository.findAllByOrderByCreatedAtDesc().map { it.toSimpleResponse() }
        }
        return todoRepository.findAllByOrderByCreatedAt().map { it.toSimpleResponse() }
    }

    @Transactional
    fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        todoRepository.delete(todo)
    }

    @Transactional
    fun finishTodo(todoId: Long): TodoSimpleResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        return todo.finishTodo().toSimpleResponse()
    }

    @Transactional
    fun updateTodo(todoId: Long, todoUpdateRequest: TodoUpdateRequest): TodoSimpleResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        return todo.updateTodo(todoUpdateRequest).toSimpleResponse()
    }
}