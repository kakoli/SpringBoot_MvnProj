package com.example.springdata.emp.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String s, Throwable e)
    {
        super(s,e);
    }

    public NotFoundException(String s)
    {
        super(s);
    }
}
