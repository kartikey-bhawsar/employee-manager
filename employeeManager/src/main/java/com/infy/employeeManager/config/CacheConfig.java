package com.infy.employeeManager.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport{
	
	@Bean
	public CacheManager ehCacheManager() {
		CacheConfiguration firstLevelCache=new CacheConfiguration();
		firstLevelCache.setName("1st-level-cache");
		firstLevelCache.setMemoryStoreEvictionPolicy("LRU");
		firstLevelCache.setMaxEntriesLocalHeap(1000);
		firstLevelCache.setTimeToLiveSeconds(300);
		
		net.sf.ehcache.config.Configuration config=new net.sf.ehcache.config.Configuration();
		config.addCache(firstLevelCache);
		return CacheManager.newInstance(config);
	}
	
	@Bean
	@Override
	public org.springframework.cache.CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}
	
}
