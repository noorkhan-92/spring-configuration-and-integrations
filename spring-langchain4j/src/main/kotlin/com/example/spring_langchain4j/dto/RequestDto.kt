package com.example.spring_langchain4j.dto

enum class Model {GEMINI, GPT, LLAMA}

data class RequestDto(val message: String, val model: Model)
