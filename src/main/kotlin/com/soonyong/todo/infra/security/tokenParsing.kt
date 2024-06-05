package com.soonyong.todo.infra.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.soonyong.todo.domain.member.dto.MemberToken
import com.soonyong.todo.infra.exception.TokenException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun tokenParsing(jsonString: String): MemberToken {
    try {
        val objectMapper = ObjectMapper()
        val memberMap: Map<String, Any> = objectMapper.readValue(jsonString, Map::class.java) as Map<String, Any>
        val memberId = (memberMap["memberId"] as Int).toLong()
        val token = memberMap["token"] as String

        // Parsing expireAt string to Timestamp
        val formatter = DateTimeFormatter.ISO_DATE_TIME

        return MemberToken(memberId, token)
    } catch (e: Exception) {
        throw TokenException("Unvaild Token Format")
    }
}