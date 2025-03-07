package com.newsaggregator.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.newsaggregator.core.utilities.HashingUtils;

@Service
public class NewsCacheManager implements NewsCacheService {
	private final CacheManager cacheManager;
	private static final String NEWS_CACHE_NAME = "newsCache";

	public NewsCacheManager(CacheManager cacheManager) {

		this.cacheManager = cacheManager;
	}

	public boolean isUrlCached(String url) {
		String hashUrl = HashingUtils.hashUrl(url);
		Cache cache = cacheManager.getCache(NEWS_CACHE_NAME);
		return cache != null && cache.get("news:" + hashUrl) != null;
	}

	public void cacheUrl(String url) {
		String hashUrl = HashingUtils.hashUrl(url);
		Cache cache = cacheManager.getCache(NEWS_CACHE_NAME);
		if (cache != null) {
			cache.put("news:" + hashUrl, "cached");
		}
	}
}
