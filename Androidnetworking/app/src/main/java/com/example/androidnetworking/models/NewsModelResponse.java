package com.example.androidnetworking.models;

public class NewsModelResponse {
private int id, user_id, topic_id;
private String title, content,image,create_at;

    public NewsModelResponse() {
    }

    public NewsModelResponse(int id, int user_id, int topic_id, String title, String content, String image, String create_at) {
        this.id = id;
        this.user_id = user_id;
        this.topic_id = topic_id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.create_at = create_at;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
