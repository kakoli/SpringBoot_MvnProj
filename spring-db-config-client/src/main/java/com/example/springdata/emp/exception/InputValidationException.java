package com.example.springdata.emp.exception;

public class InputValidationException extends RuntimeException {

    public InputValidationException(String s, Throwable e)
    {
        super(s,e);
    }

    public InputValidationException(String s)
    {
        super(s);
    }
}
