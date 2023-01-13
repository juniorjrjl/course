package com.ead.course.exception;

public class DomainNotFoundException extends CourseException{
    public DomainNotFoundException(final String message) {
        super(message);
    }
}
