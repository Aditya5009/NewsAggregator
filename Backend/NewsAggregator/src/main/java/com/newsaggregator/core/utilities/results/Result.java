package com.newsaggregator.core.utilities.results;


import lombok.Getter;
import java.io.Serializable;

@Getter
public class Result implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final boolean success;
    private String message;

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, String message) {
        this(success);
        this.message = message;
    }

}
