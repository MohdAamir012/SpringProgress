package com.jvm.Week7.Exception;


//Here using the concept of sealed classes and record class
public sealed interface EmployeeResponse permits EmployeeResponse.Success, EmployeeResponse.Error {

    record Success<T>(T data) implements EmployeeResponse { }
    record Error(String message, int code) implements EmployeeResponse { }
}
