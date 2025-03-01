package com.newsaggregator.core.utilities.results;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class DataResult<T> extends Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final T data;

	public DataResult(T data, boolean success, String message) {
		super(success, message);
		this.data = data;
	}

	public DataResult(T data, boolean success) {
		super(success);
		this.data = data;
	}

}
