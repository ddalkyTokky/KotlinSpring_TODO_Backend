package com.soonyong.todo.domain.todo.controller;

import com.soonyong.todo.domain.member.service.MemberService
import com.soonyong.todo.domain.todo.dto.*
import com.soonyong.todo.domain.todo.service.TodoService
import com.soonyong.todo.infra.exception.TokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

@RequestMapping("/todos")
@RestController
class TodoController (
    private val todoService: TodoService,
    private val memberService: MemberService
) {
    @PostMapping()
    fun createTodo(
        @RequestBody @Valid createTodoRequest: TodoRequest,
        @RequestHeader httpsHeaders: HttpHeaders
    ): ResponseEntity<TodoSimpleResponse> {
        val token: String = httpsHeaders.get("Authorization")?.get(0) ?: throw TokenException("No Token Found")
        val memberName: String = memberService.tokenValidation(token).getOrNull()?.payload?.get("memberName").toString()

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.createTodo(createTodoRequest, memberName))
    }

    @GetMapping()
    fun getTodoList(@RequestParam params: Map<String, String>): ResponseEntity<Page<TodoSimpleResponse>?> {
        val todoSearch: TodoSearch = TodoSearch(
            params.get("order") ?: "ascend",
            params.get("member"),
            params.get("page")?.toInt() ?: 0,
            params.get("page-size")?.toInt() ?: 10
        )
        Assert.isTrue(
            todoSearch.order.equals("descend") ||
                    todoSearch.order.equals("ascend"),
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
        @RequestBody @Valid todoRequest: TodoRequest,
        @RequestHeader httpsHeaders: HttpHeaders
    ): ResponseEntity<TodoSimpleResponse> {
        val token: String = httpsHeaders.get("Authorization")?.get(0) ?: throw TokenException("No Token Found")
        val memberName: String = memberService.tokenValidation(token).getOrNull()?.payload?.get("memberName").toString()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.updateTodo(todoId, memberName, todoRequest))
    }

    @PatchMapping("/{todoId}")
    fun finishTodo(
        @PathVariable todoId: Long,
        @RequestHeader httpsHeaders: HttpHeaders
    ): ResponseEntity<TodoSimpleResponse> {
        val token: String = httpsHeaders.get("Authorization")?.get(0) ?: throw TokenException("No Token Found")
        val memberName: String = memberService.tokenValidation(token).getOrNull()?.payload?.get("memberName").toString()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todoService.finishTodo(todoId, memberName))
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(
        @PathVariable todoId: Long,
        @RequestHeader httpsHeaders: HttpHeaders
    ): ResponseEntity<Unit> {
        val token: String = httpsHeaders.get("Authorization")?.get(0) ?: throw TokenException("No Token Found")
        val memberName: String = memberService.tokenValidation(token).getOrNull()?.payload?.get("memberName").toString()

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(todoService.deleteTodo(todoId, memberName))
    }
}
