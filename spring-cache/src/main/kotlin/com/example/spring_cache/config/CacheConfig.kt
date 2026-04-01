package com.example.spring_cache.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.github.benmanes.caffeine.cache.Caffeine
import com.hazelcast.config.Config
import com.hazelcast.config.EvictionConfig
import com.hazelcast.config.EvictionPolicy
import com.hazelcast.config.JoinConfig
import com.hazelcast.config.MapConfig
import com.hazelcast.config.MaxSizePolicy
import com.hazelcast.config.MulticastConfig
import com.hazelcast.config.NetworkConfig
import com.hazelcast.config.TcpIpConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import java.util.concurrent.TimeUnit

@Configuration
class CacheConfig {

    @Value("\${cache.ttl}")
    private val cacheTTL: Int = 1

    @Value("\${cache.size}")
    private val cacheSize: Int = 1

    @Value("\${cache.initial-capacity}")
    private val initialCapacity: Int = 100

    @Value("\${cache.max-idle}")
    private val cacheMaxIdle: Int = 1

    @Bean
    @ConditionalOnProperty(value = ["hazelcast.enable"], prefix = "cache", havingValue = "true", matchIfMissing = true)
    fun hazelcastConfig(): Config {
        println("hazelcast: ttl: $cacheTTL , size: $cacheSize , maxIdle: $cacheMaxIdle")
        return Config().setClusterName("hazelcast-cluster")
            .setNetworkConfig(NetworkConfig().setPort(5701).setPortAutoIncrement(true).setJoin(
                JoinConfig().setMulticastConfig(MulticastConfig().setEnabled(false))
                    .setTcpIpConfig(TcpIpConfig().setEnabled(true).addMember("127.0.0.1").setRequiredMember("127.0.0.1"))
            ))
//            serialization config SampleSerializer was created for debugging purpose and is not compulsory needed.
//            just keeping them for reference.
//            .setSerializationConfig(SerializationConfig().addSerializerConfig(
//                SerializerConfig().setTypeClass(SampleDto::class.java)
//                    .setImplementation(SampleSerializer())
//            ))
            .addMapConfig(
            MapConfig("sample").setTimeToLiveSeconds(cacheTTL)
                .setEvictionConfig(
                    EvictionConfig().setEvictionPolicy(EvictionPolicy.LRU)
//                         -- FREE_HEAP_SIZE is not working and ENTRY_COUNT is not supported by IMap so used PER_NODE
                        .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
                        .setSize(cacheSize)
                )
                .setMaxIdleSeconds(cacheMaxIdle)
                .setBackupCount(1)
        )
    }

//    the following beans was created for debugging purpose but is not required and is working without these beans
//    @Bean
//    @ConditionalOnProperty(value = ["hazelcast.enable"], prefix = "cache", havingValue = "true", matchIfMissing = true)
//    fun hazelcastInstance(config: Config): HazelcastInstance {
//        println("hazelcast config: $config")
//        return Hazelcast.newHazelcastInstance(config)
//    }
//
//    @Bean
//    @ConditionalOnProperty(value = ["hazelcast.enable"], prefix = "cache", havingValue = "true", matchIfMissing = true)
//    fun cacheManager(hz: HazelcastInstance): CacheManager {
//        println("hazelcast instance: $hz")
//        return HazelcastCacheManager(hz)
//    }

    @Bean
    @ConditionalOnProperty(value = ["caffeine.enable"], prefix = "cache", havingValue = "true", matchIfMissing = false)
    fun caffeineConfig(): CacheManager {
        println("caffeine: ttl: $cacheTTL , initial-capacity: $initialCapacity , size: $cacheSize , maxIdle: $cacheMaxIdle")
        val caffeineManager = CaffeineCacheManager("sample")
        caffeineManager.setCaffeine(
            Caffeine.newBuilder()
                .initialCapacity(initialCapacity)
                .maximumSize(cacheSize.toLong())
                .expireAfterWrite(cacheTTL.toLong(), TimeUnit.SECONDS)
                .recordStats()
        )
        return caffeineManager
    }
}