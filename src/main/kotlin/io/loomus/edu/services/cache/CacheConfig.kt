package io.loomus.edu.services.cache

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import java.time.Duration

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun redisCacheManagerBuilderCustomizer() = RedisCacheManagerBuilderCustomizer {
        val configurationMap = HashMap<String, RedisCacheConfiguration>()

        configurationMap[CacheName.userRoles] = getConfig(Duration.ofHours(1))
        configurationMap[CacheName.course] = getConfig(Duration.ofHours(1))
        configurationMap[CacheName.coursePermission] = getConfig(Duration.ofHours(1))

        it.withInitialCacheConfigurations(configurationMap)
    }


    private fun getConfig(duration: Duration) =
        RedisCacheConfiguration.defaultCacheConfig().entryTtl(duration)

}