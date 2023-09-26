/*package com.hepsijet.kargo2.JWT.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.swing.plaf.PanelUI;

@Configuration
public class CacheConfig extends CachingConfigurerSupport {
        @Primary
        @Bean(name = "cacheManager1")
        public CacheManager cacheManager1() {
            return new ConcurrentMapCacheManager("xdock");
        }

        @Bean(name = "cacheManager2")
        public CacheManager alternateCacheManager() {
            return new ConcurrentMapCacheManager("city-zip-cache");
        }


}

 */

