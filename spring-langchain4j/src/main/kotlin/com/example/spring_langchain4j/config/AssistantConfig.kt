package com.example.spring_langchain4j.config

import dev.langchain4j.memory.ChatMemory
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class AssistantConfig {
    @Bean
    @Scope(SCOPE_PROTOTYPE)
    fun chatMemory(): ChatMemory {
        return MessageWindowChatMemory.withMaxMessages(10)
    }
}