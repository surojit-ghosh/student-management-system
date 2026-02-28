package com.capgemini.exception;

public class InvalidStudentDataException extends Exception {

    public InvalidStudentDataException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "InvalidStudentDataException: " + getMessage();
    }
}
