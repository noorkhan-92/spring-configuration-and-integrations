package com.example.spring_langchain4j.controller

import com.example.spring_langchain4j.dto.Model
import com.example.spring_langchain4j.dto.RequestDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChatControllerTest(@Autowired val chatController: ChatController) {
    @Test
    fun testChat() {
        val response = chatController.chat(RequestDto("what is the capital of Pakistan?", Model.GEMINI))
        println(response)
        assertThat(response).containsIgnoringCase("Islamabad")
    }

    @Test
    fun testChatMemory() {
        val response = chatController.chat(RequestDto("what is the capital of Pakistan?", Model.GEMINI))
        assertThat(response).containsIgnoringCase("Islamabad")
        val memoryResponse = chatController.chat(RequestDto("and what is its area?", Model.GEMINI))
        assertThat(memoryResponse).containsIgnoringCase("Islamabad")
    }

    @Test
    fun testResult() {
        val result = chatController.result("Java")
        println(result)
        println(result.content())
        println(result.sources())
        println(result.tokenUsage())
        println(result.toolExecutions())
        println(result.finalResponse())
        println(result.finishReason())
        println(result.intermediateResponses())
        println(result.toString())
    }

    @Test
    fun testTool() {
        val response = chatController.calculate("what is product of 3, 5")
        println(response)
        assertThat(response).isEqualTo(15)
    }

    @Test
    fun testAgents() {
        var reqResponse = "Lets have a discussion on Artificial General Intelligence (AGI)."
        repeat(5) {
            reqResponse = chatController.callAgent(reqResponse, "firstExpert")
            println(reqResponse)
            reqResponse = chatController.callAgent(reqResponse, "secondExpert")
            println(reqResponse)
        }
    }
}