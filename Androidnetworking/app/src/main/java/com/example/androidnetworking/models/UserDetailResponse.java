package com.example.androidnetworking.models;

public class UserDetailResponse {
//     "id": 3,
//             "email": "khang@gmail.com",
//             "name": "Nguyen van c",
//             "avatar": "https://www.w3schools.com/howto/img_avatar.png"
    private int id;
    private String email,name,avatar;

    public UserDetailResponse() {
    }

    public UserDetailResponse(int id, String email, String name, String avatar) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
