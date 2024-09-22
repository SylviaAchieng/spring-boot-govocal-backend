package com.example.engagement_platform.exception;

public class NoSuchRecordFoundException extends RuntimeException {
    public NoSuchRecordFoundException(String message) {
        super(message);
    }
}
