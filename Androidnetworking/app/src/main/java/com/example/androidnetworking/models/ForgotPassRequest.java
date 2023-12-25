package com.example.androidnetworking.models;

public class ForgotPassRequest {

    private String email;

    public ForgotPassRequest() {
    }

    public ForgotPassRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
