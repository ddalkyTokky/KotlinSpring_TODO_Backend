package com.soonyong.todo.infra.security

import java.security.MessageDigest

fun sha256(base: String): String {
    try {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
        val hash: ByteArray = digest.digest(base.toByteArray(charset("UTF-8")))
        val hexString = StringBuilder()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    } catch (ex: Exception) {
        throw RuntimeException(ex)
    }
}