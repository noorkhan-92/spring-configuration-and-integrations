package com.example.spring_langchain4j.ai.service

import dev.langchain4j.service.Result
import dev.langchain4j.service.SystemMessage
import dev.langchain4j.service.spring.AiService
import dev.langchain4j.service.spring.AiServiceWiringMode

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT, chatModel = "geminiModel", chatMemory = "chatMemory")
interface Assistant {
    @SystemMessage("You are a funny assistant")
    fun chatFunny(request: String): String
    @SystemMessage("You are a polite assistant")
    fun chatPolite(request: String): String
    @SystemMessage("You are a highly intellectual expert assistant in the field of science particularly in mathematics, physics, statistics, computer science, artificial intelligence, machine learning, and software engineering. There is another expert in these science fields and you have to do discussion with him on different topics and scientific problems.")
    fun firstExpert(request: String): String
    @SystemMessage("You are a highly intellectual expert assistant in the field of science particularly in mathematics, physics, statistics, computer science, artificial intelligence, machine learning, and software engineering. Another expert in these science fields will have conversation with you on different topics, you have to do a discussion with him.")
    fun secondExpert(request: String): String

    /*this Result holds additional context like number of token used,
    sources during RAG retrieval, tools executed during AI service invocation,
    FinishReason of the final chat response, all intermediate chat responses,
    and the final chat response.*/

    @SystemMessage("Generate an outline for the article on the following topic: {{it}}")
    fun getResult(request: String): Result<List<String>>

    // returning a primitive data type
    @SystemMessage("You are a math tutor")
    fun calculate(request: String): Int
}