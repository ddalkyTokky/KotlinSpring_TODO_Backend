package com.soonyong.todo.infra.exception

data class ModelNotFoundException(
    val modelName: String,
    val input: String
): RuntimeException("Model ${modelName} not found with id ${input}")