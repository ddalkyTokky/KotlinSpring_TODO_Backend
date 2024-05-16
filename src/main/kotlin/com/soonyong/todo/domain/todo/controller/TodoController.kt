package com.soonyong.todo.domain.todo.controller;

import com.soonyong.todo.domain.todo.service.TodoService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/courses")
@RestController
public class TodoController (
    private val todoService: TodoService
){

}
