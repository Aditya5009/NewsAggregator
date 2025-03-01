package com.newsaggregator.service.fetch;

import com.newsaggregator.exception.FetchNewsExceptions;

public interface FetchNews {

	String fetchNews(String url) throws FetchNewsExceptions;
}
