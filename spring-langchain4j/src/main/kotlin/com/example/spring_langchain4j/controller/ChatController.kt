package com.example.spring_langchain4j.controller

import com.example.spring_langchain4j.dto.RequestDto
import com.example.spring_langchain4j.service.ModelService
import dev.langchain4j.service.Result
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// the rest apis are not properly added as this class is just for testing and I call it directly from test classes
@RestController
@RequestMapping("/chat")
class ChatController(val modelService: ModelService) {
    fun chat(@RequestBody request: RequestDto): String {
        return modelService.chat(request.message)
    }

    fun result(request: String): Result<List<String>> {
        return modelService.result(request)
    }

    fun calculate(request: String): Int {
        return modelService.calculate(request)
    }

    fun callAgent(request: String, agentName: String): String {
        return modelService.callAgent(request, agentName)
    }
}