package com.soonyong.todo.domain.reply.repository

import com.soonyong.todo.domain.reply.model.Reply
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository: JpaRepository<Reply, Long> {
}