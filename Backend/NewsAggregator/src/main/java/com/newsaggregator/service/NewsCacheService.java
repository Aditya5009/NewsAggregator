package com.newsaggregator.service;

public interface NewsCacheService {
	boolean isUrlCached(String url);

	void cacheUrl(String url);
}
