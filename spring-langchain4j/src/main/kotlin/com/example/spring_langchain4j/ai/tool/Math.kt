package com.example.spring_langchain4j.ai.tool

import dev.langchain4j.agent.tool.Tool
import org.springframework.stereotype.Component

@Component
class Math {
    @Tool
    fun sum(a: Int, b: Int): Int {
        println("sum tool is called")
        return a + b
    }
    @Tool
    fun product(a: Int, b: Int): Int {
        println("product tool is called")
        return a * b
    }
}