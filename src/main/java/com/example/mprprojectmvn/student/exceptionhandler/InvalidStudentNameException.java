package com.example.mprprojectmvn.student.exceptionhandler;


public class InvalidStudentNameException extends RuntimeException {
    public InvalidStudentNameException(String s) {
        super(s);
    }
}
