package com.example.androidnetworking.models;

public class UserLoginResponse {
    //user nhận về
    private Boolean status;
    private  UserModel user;

    public UserLoginResponse(Boolean status, UserModel user) {
        this.status = status;
        this.user = user;
    }

    public UserLoginResponse() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
