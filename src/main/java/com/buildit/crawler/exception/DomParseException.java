package com.buildit.crawler.exception;

public class DomParseException extends Exception {
    public DomParseException(String message, Exception e) {
        super(message, e);
    }
}
