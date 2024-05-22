package com.soonyong.todo.domain.todo.controller;

import com.soonyong.todo.domain.todo.dto.*
import com.soonyong.todo.domain.todo.service.TodoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos")
@RestController
public class TodoController (
    private val todoService: TodoService
){
    @PostMapping()
    fun createTodo(@RequestBody @Valid createTodoRequest: TodoRequest): ResponseEntity<TodoSimpleResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.createTodo(createTodoRequest))
    }

    @GetMapping()
    fun getTodoList(@RequestParam params: Map<String, String>): ResponseEntity<List<TodoSimpleResponse>?> {
        val todoSearch: TodoSearch = TodoSearch(params.get("order"), params.get("member"))

        Assert.isTrue(
            todoSearch.order.equals("descend") ||
                    todoSearch.order.equals("ascend") ||
                    (todoSearch.order == null),
            "BAD_REQUEST from query: order has to be one of (descend, ascend)"
        )

        Assert.isTrue(
            (todoSearch.member?.isBlank() == false) ||
                    (todoSearch.member == null),
            "BAD_REQUEST from query: member should not be Blank"
        )

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getAllTodoList(todoSearch))
    }

    @GetMapping("/{todoId}")
    fun getTodo(@PathVariable todoId: Long): ResponseEntity<TodoDetailResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                todoService
                    .getTodoById(todoId)
                    .toDetailResponse()
            )
    }

    @PutMapping("/{todoId}")
    fun updateTodo(
        @PathVariable todoId: Long,
        @RequestBody @Valid todoRequest: TodoRequest
    ): ResponseEntity<TodoSimpleResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.updateTodo(todoId, todoRequest))
    }

    @PatchMapping("/{todoId}")
    fun finishTodo(@PathVariable todoId: Long): ResponseEntity<TodoSimpleResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.finishTodo(todoId))
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(
        @PathVariable todoId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(todoService.deleteTodo(todoId))
    }
}
