package com.newsaggregator.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.newsaggregator.controller.validation.NewsNameConstraint;
import com.newsaggregator.core.utilities.results.DataResult;
import com.newsaggregator.model.dtos.responses.GetAllNewsResponse;
import com.newsaggregator.model.dtos.responses.GetByUUID;
import com.newsaggregator.model.dtos.responses.GetNewsByPublisherResponse;
import com.newsaggregator.model.dtos.responses.GetPagination;
import com.newsaggregator.service.NewsService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/news")
@AllArgsConstructor
@Validated
public class NewsController {
	private final NewsService newsService;

	@GetMapping("/getAllNewsResponses")
	@Cacheable(value = "getAllNewsResponses")
	@RateLimiter(name = "newsRateLimiter")
	public DataResult<List<GetAllNewsResponse>> getAllNewsResponses() {

		return newsService.getAllNewsResponses();

	}

	@GetMapping("/publisher/{publisher}")
	@Cacheable(value = "getNewsByPublisherResponses")
	@RateLimiter(name = "newsRateLimiter")
	public DataResult<List<GetNewsByPublisherResponse>> getNewsByPublisherResponses(
			@PathVariable @NewsNameConstraint String publisher) {
		return newsService.getNewsByPublisherResponses(publisher);
	}

	@GetMapping("/getNewsPageable")
	@Cacheable(value = "getNewsPageable")
	@RateLimiter(name = "newsRateLimiter")
	public DataResult<Page<GetPagination>> getNewsPageable(@RequestParam int page, @RequestParam int size) {
		return newsService.getNewsPageable(PageRequest.of(page, size, Sort.by("publishedDate")));
	}

	@GetMapping("/findByUUID")
	@Cacheable(value = "findByUUID")
	@RateLimiter(name = "newsRateLimiter")
	public DataResult<GetByUUID> findByNewsId(@RequestParam String uuid) {
		return newsService.findByNewsId(uuid);
	}

}
