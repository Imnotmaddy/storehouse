package com.itechart.studlab.app.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.itechart.studlab.app.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.Act.class.getName(), jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.Act.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.Storehouse.class.getName(), jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.Storehouse.class.getName() + ".rooms", jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.StorageRoom.class.getName(), jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.StorageRoom.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.Transporter.class.getName(), jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.Transporter.class.getName() + ".vehicles", jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.Transport.class.getName(), jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.TTN.class.getName(), jcacheConfiguration);
            cm.createCache(com.itechart.studlab.app.domain.TTN.class.getName() + ".products", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
