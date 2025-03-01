package com.newsaggregator.core.utilities.results;

public class ErrorDataResult<T> extends DataResult<T>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ErrorDataResult(T data, String message) {
        super(data,false,message);
    }
    public ErrorDataResult(T data){
        super(data,false);
    }
    public ErrorDataResult(String message) {
        super(null,false,message);
    }
    public ErrorDataResult() {
        super(null,false);
    }
}
