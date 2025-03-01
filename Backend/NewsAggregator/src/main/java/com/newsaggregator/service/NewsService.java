package com.newsaggregator.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.newsaggregator.core.utilities.results.DataResult;
import com.newsaggregator.model.dtos.responses.GetAllNewsResponse;
import com.newsaggregator.model.dtos.responses.GetByUUID;
import com.newsaggregator.model.dtos.responses.GetNewsByPublisherResponse;
import com.newsaggregator.model.dtos.responses.GetPagination;

import java.util.List;

public interface NewsService {
	DataResult<List<GetAllNewsResponse>> getAllNewsResponses();

	DataResult<List<GetNewsByPublisherResponse>> getNewsByPublisherResponses(String publisher);

	DataResult<Page<GetPagination>> getNewsPageable(Pageable pageable);

	DataResult<GetByUUID> findByNewsId(String title);
}
