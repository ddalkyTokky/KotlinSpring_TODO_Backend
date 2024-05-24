package com.soonyong.todo.domain.todo.service;

import com.soonyong.todo.domain.member.service.MemberService
import com.soonyong.todo.domain.todo.dto.TodoRequest
import com.soonyong.todo.domain.todo.dto.TodoSearch
import com.soonyong.todo.domain.todo.dto.TodoSimpleResponse
import com.soonyong.todo.domain.todo.model.Todo
import com.soonyong.todo.domain.todo.repository.TodoRepository
import com.soonyong.todo.infra.exception.ModelNotFoundException
import com.soonyong.todo.infra.exception.TokenException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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
    fun createTodo(
        todoRequest: TodoRequest,
        memberId: Long
    ): TodoSimpleResponse {
        return todoRepository.save(
            Todo.createTodo(
                todoRequest,
                memberService.getMemberById(memberId)
            )
        ).toSimpleResponse()
    }

    fun getAllTodoList(todoSearch: TodoSearch): Page<TodoSimpleResponse>? {
        if (todoSearch.order.equals("ascend")) {
            if (todoSearch.member == null) {
                return todoRepository
                    .findAllByOrderByCreatedAtAsc(
                        PageRequest.of(
                            todoSearch.page,
                            todoSearch.pageSize
                        )
                    )
                    .map { it.toSimpleResponse() }
            }
            return todoRepository
                .findAllByMemberNameOrderByCreatedAtAsc(
                    todoSearch.member,
                    PageRequest.of(
                        todoSearch.page,
                        todoSearch.pageSize
                    )
                )
                .map { it.toSimpleResponse() }
        } else if (todoSearch.order.equals("descend")) {
            if (todoSearch.member == null) {
                return todoRepository
                    .findAllByOrderByCreatedAtDesc(
                        PageRequest.of(
                            todoSearch.page,
                            todoSearch.pageSize
                        )
                    )
                    .map { it.toSimpleResponse() }
            }
            return todoRepository
                .findAllByMemberNameOrderByCreatedAtDesc(
                    todoSearch.member,
                    PageRequest.of(
                        todoSearch.page,
                        todoSearch.pageSize
                    )
                ).map { it.toSimpleResponse() }
        }
        return null
    }

    @Transactional
    fun deleteTodo(todoId: Long, memberId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        if(memberId != todo.member!!.id) {
            throw TokenException("UnAuthorized Access Token")
        }
        todoRepository.delete(todo)
    }

    @Transactional
    fun finishTodo(todoId: Long, memberId: Long): TodoSimpleResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        if(memberId != todo.member!!.id) {
            throw TokenException("UnAuthorized Access Token")
        }
        return todo.finishTodo().toSimpleResponse()
    }

    @Transactional
    fun updateTodo(todoId: Long, memberId: Long, todoRequest: TodoRequest): TodoSimpleResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw ModelNotFoundException("Todo", todoId.toString())
        if(memberId != todo.member!!.id) {
            throw TokenException("UnAuthorized Access Token")
        }
        return todo.updateTodo(todoRequest).toSimpleResponse()
    }
}