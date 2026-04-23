package com.example.spring_langchain4j.service

import com.example.spring_langchain4j.ai.service.Assistant
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.service.Result
import org.springframework.stereotype.Service

@Service
class ModelService(val models: List<ChatModel>, val assistant: Assistant) {
    fun chat(request: String): String {
        models.forEach {
            println(it.toString())
        }
        // we can directly call model.chat(request) but assistant (AiInterface) provide additional system message which signal the given assistant job and expertise
        return assistant.chatPolite(request)
    }

    fun result(request: String): Result<List<String>> {
        return assistant.getResult(request)
    }

    fun calculate(request: String): Int {
        return assistant.calculate(request)
    }

    fun callAgent(request: String, agentName: String): String {
        return if (agentName == "firstExpert") {
            assistant.firstExpert(request)
        } else {
            assistant.secondExpert(request)
        }
    }
}