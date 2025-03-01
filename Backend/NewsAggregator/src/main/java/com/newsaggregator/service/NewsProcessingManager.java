package com.newsaggregator.service;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import com.newsaggregator.exception.FetchNewsExceptions;
import com.newsaggregator.model.entities.News;
import com.newsaggregator.service.fetch.FetchNews;
import com.newsaggregator.service.parse.NewsParser;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NewsProcessingManager {
	private final NewsCacheService newsCacheService;
	private final FetchNews fetchClient;

	public List<News> fetchAndProcessNews(String url, NewsParser newsParser) throws FetchNewsExceptions {
		String data = fetchClient.fetchNews(url);
		JSONObject jsonObject = xmlToJson(data);

		return newsParser.parse(jsonObject).stream()
				.filter(news -> news.getContent() != null && !news.getContent().trim().isEmpty()).filter(news -> {
					boolean isCached = newsCacheService.isUrlCached(news.getLink());
					if (!isCached) {
						newsCacheService.cacheUrl(news.getLink());
					}
					return !isCached;
				}).collect(Collectors.toList());
	}

	private JSONObject xmlToJson(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = XML.toJSONObject(data);
		return jsonObject;
	}

}
