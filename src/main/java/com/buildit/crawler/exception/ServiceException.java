package com.buildit.crawler.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message, Exception e) {
        super(message, e);
    }
    public ServiceException(Exception e) {
        super(e);
    }
}
