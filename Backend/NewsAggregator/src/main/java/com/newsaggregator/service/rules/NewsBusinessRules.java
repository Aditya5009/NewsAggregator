package com.newsaggregator.service.rules;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.newsaggregator.exception.BusinessException;
import com.newsaggregator.repository.NewsRepository;

@Service
@AllArgsConstructor
public class NewsBusinessRules {
	private final NewsRepository repository;

	public void checkIfPublisherExists(String publisher) {
		if (!repository.existsByPublisher(publisher)) {
			throw new BusinessException("Publisher is not found");
		}
	}

}
