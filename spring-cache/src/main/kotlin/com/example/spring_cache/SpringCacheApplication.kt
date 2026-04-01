package com.example.spring_cache

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class SpringCacheApplication

fun main(args: Array<String>) {
	runApplication<SpringCacheApplication>(*args)
}
