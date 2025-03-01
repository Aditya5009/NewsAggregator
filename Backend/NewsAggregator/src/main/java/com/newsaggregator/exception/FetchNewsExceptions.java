package com.newsaggregator.exception;

import lombok.Getter;

import java.io.IOException;

@Getter
public class FetchNewsExceptions extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int statusCode;

	public FetchNewsExceptions(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

}
