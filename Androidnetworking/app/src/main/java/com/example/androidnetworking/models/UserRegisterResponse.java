package com.example.androidnetworking.models;

public class UserRegisterResponse {
    private String message;

    public UserRegisterResponse() {
    }

    public UserRegisterResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
