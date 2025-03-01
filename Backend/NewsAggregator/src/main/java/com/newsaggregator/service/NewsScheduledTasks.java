package com.newsaggregator.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.newsaggregator.constant.Constants;
import com.newsaggregator.service.parse.NewsParser;

import java.util.Map;

@Component
@AllArgsConstructor
public class NewsScheduledTasks {
	private final NewsManager newsManager;
	private final Map<String, NewsParser> newsParserMap;
	private final Constants constants;

	@Scheduled(fixedRate = 1800000)
	@CacheEvict(value = { "getNewsByPublisherResponses", "getAllNewsResponses", "getNewsPageable",
			"findByTitle" }, allEntries = true)
	public void fetchAndSaveNews() {
		try {
			newsManager.saveNews(constants.getBbcUrl(), newsParserMap.get("bbc"));
			newsManager.saveNews(constants.getNtvUrl(), newsParserMap.get("ntv"));
			newsManager.saveNews(constants.getSozcu(), newsParserMap.get("sozcu"));
			System.out.println("worked");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
