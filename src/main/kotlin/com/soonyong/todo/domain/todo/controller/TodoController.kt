package com.soonyong.todo.domain.todo.controller;

import com.soonyong.todo.domain.todo.dto.TodoCreateRequest
import com.soonyong.todo.domain.todo.dto.TodoDetailResponse
import com.soonyong.todo.domain.todo.dto.TodoSimpleResponse
import com.soonyong.todo.domain.todo.dto.TodoUpdateRequest
import com.soonyong.todo.domain.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/todo")
@RestController
public class TodoController (
    private val todoService: TodoService
){
    @PostMapping("/new")
    fun createTodo(@RequestBody createTodoRequest: TodoCreateRequest): ResponseEntity<TodoSimpleResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.createTodo(createTodoRequest))
    }

    @GetMapping("/{todoId}")
    fun getTodo(@PathVariable todoId: Long): ResponseEntity<TodoDetailResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.getTodoResponseById(todoId))
    }

    @GetMapping("/list")
    fun getTodoList(@RequestParam params: Map<String, String>): ResponseEntity<List<TodoSimpleResponse>> {
        val orderBy: String? = params.get("order")
        if(orderBy.equals("ascend") or orderBy.equals("descend")){
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.getAllTodoList(orderBy!!))
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(null)
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(
        @PathVariable todoId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(todoService.deleteTodo(todoId))
    }

    @PatchMapping("/{todoId}/done")
    fun finishTodo(@PathVariable todoId: Long): ResponseEntity<TodoSimpleResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.finishTodo(todoId))
    }

    @PatchMapping("/{todoId}/edit")
    fun updateTodo(
        @PathVariable todoId: Long,
        todoUpdateRequest: TodoUpdateRequest
    ): ResponseEntity<TodoSimpleResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.updateTodo(todoId, todoUpdateRequest))
    }
}
