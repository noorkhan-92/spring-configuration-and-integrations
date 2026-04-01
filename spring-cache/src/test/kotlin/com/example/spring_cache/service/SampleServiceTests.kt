package com.example.spring_cache.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.random.Random

@SpringBootTest
@ActiveProfiles("test")
class SampleServiceTests(@Autowired private val service: SampleService) {

    @Test
    fun getSample() {
        val id = Random.nextInt(1000)
        service.getSample(id, id)
        repeat (5) {
            assertThat(service.getSample(id, Random.nextInt(1000)).name).isEqualTo("sample-$id")
        }
    }

    @Test
    fun getSampleCacheExpired() {
        val id = Random.nextInt(1000)
        service.getSample(id, id)
        Thread.sleep(5000)
        assertThat(service.getSample(id, Random.nextInt(1000)).name).isNotEqualTo("sample-$id")
    }
}