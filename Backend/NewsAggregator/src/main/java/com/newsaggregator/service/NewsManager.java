package com.newsaggregator.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.newsaggregator.core.utilities.mappers.ModelMapperService;
import com.newsaggregator.core.utilities.results.DataResult;
import com.newsaggregator.core.utilities.results.SuccessDataResult;
import com.newsaggregator.exception.FetchNewsExceptions;
import com.newsaggregator.model.dtos.responses.GetAllNewsResponse;
import com.newsaggregator.model.dtos.responses.GetByUUID;
import com.newsaggregator.model.dtos.responses.GetNewsByPublisherResponse;
import com.newsaggregator.model.dtos.responses.GetPagination;
import com.newsaggregator.model.entities.News;
import com.newsaggregator.repository.NewsRepository;
import com.newsaggregator.service.parse.NewsParser;
import com.newsaggregator.service.rules.NewsBusinessRules;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NewsManager implements NewsService {
	private final NewsRepository newsRepository;
	private final ModelMapperService modelMapperService;
	private final NewsBusinessRules businessRules;
	private final NewsProcessingManager processingManager;

	public void saveNews(String url, NewsParser parser) throws FetchNewsExceptions {
		List<News> newsList = processingManager.fetchAndProcessNews(url, parser);
		newsRepository.saveAll(newsList);
	}

	@Override
	public DataResult<List<GetAllNewsResponse>> getAllNewsResponses() {
		List<News> news = newsRepository.findAllByOrderByPublishedDateDesc();
		List<GetAllNewsResponse> allNewsResponses = news.stream()
				.map(item -> this.modelMapperService.forResponse().map(item, GetAllNewsResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(allNewsResponses, "Successful");
	}

	@Override
	public DataResult<List<GetNewsByPublisherResponse>> getNewsByPublisherResponses(String publisher) {
		this.businessRules.checkIfPublisherExists(publisher);
		List<GetNewsByPublisherResponse> getNewsByPublisherResponses = newsRepository
				.findAllByPublisherOrderByPublishedDateDesc(publisher).stream()
				.map(item -> modelMapperService.forResponse().map(item, GetNewsByPublisherResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(getNewsByPublisherResponses, "Successful");
	}

	@Override
	public DataResult<Page<GetPagination>> getNewsPageable(Pageable pageable) {
		Page<News> newsPage = newsRepository.findAll(pageable);
		Page<GetPagination> dtoPage = newsPage
				.map(item -> this.modelMapperService.forResponse().map(item, GetPagination.class));

		return new SuccessDataResult<>(dtoPage, "Successful");
	}

	@Override
	public DataResult<GetByUUID> findByNewsId(String uuid) {
		GetByUUID getByTitle = this.modelMapperService.forResponse().map(newsRepository.findById(uuid),
				GetByUUID.class);
		return new SuccessDataResult<>(getByTitle, "Successful");
	}
}
