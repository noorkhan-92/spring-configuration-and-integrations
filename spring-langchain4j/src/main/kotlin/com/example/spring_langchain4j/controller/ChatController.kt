package com.example.spring_langchain4j.controller

import com.example.spring_langchain4j.dto.RequestDto
import com.example.spring_langchain4j.service.GeminiService
import dev.langchain4j.service.Result
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class ChatController(val geminiService: GeminiService) {
    fun chat(@RequestBody request: RequestDto): String {
        return geminiService.chat(request.message)
    }

    fun result(request: String): Result<List<String>> {
        return geminiService.result(request)
    }

    fun calculate(request: String): Int {
        return geminiService.calculate(request)
    }

    fun callAgent(request: String, agentName: String): String {
        return geminiService.callAgent(request, agentName)
    }
}