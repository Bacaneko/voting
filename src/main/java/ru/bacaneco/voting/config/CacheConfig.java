package ru.bacaneco.voting.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.util.Objects;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public JCacheManagerFactoryBean jCacheManagerFactoryBean() throws URISyntaxException {
        JCacheManagerFactoryBean jCacheManagerFactoryBean = new JCacheManagerFactoryBean();
        jCacheManagerFactoryBean.setCacheManagerUri(Objects.requireNonNull(getClass().getResource("/ehcache.xml")).toURI());
        return jCacheManagerFactoryBean;
    }

    @Bean
    public JCacheCacheManager jCacheCacheManager(JCacheManagerFactoryBean jCacheManagerFactoryBean){
        JCacheCacheManager jCacheCacheManager = new JCacheCacheManager();
        jCacheCacheManager.setCacheManager(jCacheManagerFactoryBean.getObject());
        return jCacheCacheManager;
    }
}
