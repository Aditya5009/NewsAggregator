package com.newsaggregator.service.parse;

import org.json.JSONObject;

import com.newsaggregator.model.entities.News;

import java.util.List;

public interface NewsParser {

	List<News> parse(JSONObject jsonObject);
}
