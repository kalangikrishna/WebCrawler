package com.buildit.crawler.exception;

public class ClientException  extends Exception {
    public ClientException(String message, Exception e) {
        super(message, e);
    }
}
