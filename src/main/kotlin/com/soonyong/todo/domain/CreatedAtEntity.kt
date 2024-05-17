package com.soonyong.todo.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class CreatedAtEntity {
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    open var createdAt: Timestamp? = null
}