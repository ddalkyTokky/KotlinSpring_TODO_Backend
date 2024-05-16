package com.soonyong.todo.domain.todo.service;

import com.soonyong.todo.domain.todo.dto.TodoResponse
import com.soonyong.todo.domain.todo.model.TodoStatus
import org.springframework.stereotype.Service;
import java.sql.Timestamp

@Service
public class TodoService {
    fun getTodoById(todoId: Long): TodoResponse {
        // TODO
        return TodoResponse("title", "content", Timestamp(389L), "author", TodoStatus.DONE)
    }
}
