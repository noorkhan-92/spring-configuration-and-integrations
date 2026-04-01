package com.example.spring_cache.service

import com.example.spring_cache.data.dto.SampleDto
import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class SampleService(val cacheManager: CacheManager) {

    private val logger = LoggerFactory.getLogger(SampleService::class.java)

//    private val cacheManager: CacheManager

    @Cacheable("sample", key = "#id")
    fun getSample(id: Int, random: Int): SampleDto {
        logger.info("Getting sample")
        logger.info("cache manager is : {}", cacheManager.javaClass.name)
        return SampleDto(id, "sample-$random", "sample description")
    }
}