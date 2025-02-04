package com.jvm.Week9.Exception;


//Here using the concept of sealed classes and record class
public sealed interface UserResponse permits UserResponse.Success, UserResponse.Error {

    record Success<T>(T data) implements UserResponse { }
    record Error(String message, int code) implements UserResponse { }
}
