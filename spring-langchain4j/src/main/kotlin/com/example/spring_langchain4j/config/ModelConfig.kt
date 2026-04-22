package com.example.spring_langchain4j.config

import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.chat.request.ResponseFormat
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.model.openai.OpenAiChatModel
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class ModelConfig {

    @Value("\${google.gemini.api-key}")
    val apiKey: String = ""
    @Value("\${langchain4j.open-ai.chat-model.api-key}")
    val openAiApikey: String = ""

    @Value("\${groq.api-key}")
    val groqApikey: String = ""

    @Bean
    fun geminiModel(): ChatModel {
        return GoogleAiGeminiChatModel.builder()
            .apiKey(apiKey)
            .modelName("gemini-2.5-flash-lite")
            .maxOutputTokens(1000) // response max length
            .responseFormat(ResponseFormat.TEXT)
            .build()
    }

    @Bean
    fun openaiModel(): ChatModel {
        return OpenAiChatModel.builder()
            .baseUrl("https://api.openai.com/v1")
            .apiKey(openAiApikey)
            .modelName("gpt-5.4-mini")
            .build()
    }

    // this model is compatible with openai and can be used openai config with just changing url and key
    @Bean
    fun groqModel(): ChatModel {
        return OpenAiChatModel.builder()
            .baseUrl("https://api.groq.com/openai/v1")
            .apiKey(groqApikey)
            .modelName("openai/gpt-oss-120b")
            .build()
    }

    @Bean
    fun ollamaModel(): ChatModel {
        return OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("llama3.2:1b")
            .topK(1000)
            .temperature(1.0)
            .timeout(Duration.ofSeconds(30))
            .build()
    }
}