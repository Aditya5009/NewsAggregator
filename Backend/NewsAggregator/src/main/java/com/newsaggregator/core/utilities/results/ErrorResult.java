package com.newsaggregator.core.utilities.results;

import lombok.Setter;

@Setter
public class ErrorResult extends Result {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorResult() {
		super(false);
	}

	public ErrorResult(String message) {
		super(false, message);
	}
}