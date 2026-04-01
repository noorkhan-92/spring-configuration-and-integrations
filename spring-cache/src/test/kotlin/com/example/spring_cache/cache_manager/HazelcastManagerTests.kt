package com.example.spring_cache.cache_manager

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("hazelcast")
class HazelcastManagerTests(@Autowired private val cacheManager: CacheManager) {
    @Test
    fun getCacheManager() {
        val cache = cacheManager.getCache("sample")
        cache?.put("sample-1", "sample-value")
        println(cache?.get("sample-1")?.get())
        assertThat(cache).isNotNull
        println(cacheManager.cacheNames)
        cacheManager.getCache("sample")?.put(1, "hello")
        cache?.put(1, "hello")
        println(cache?.get(1))
        println(cacheManager.javaClass)
        assertThat(cacheManager.javaClass.name).containsIgnoringCase("hazelcast")
    }
}