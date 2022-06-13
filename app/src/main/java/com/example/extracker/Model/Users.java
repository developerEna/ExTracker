package com.example.extracker.Model;

public class Users {

    String username, id;

    public Users(String username, String id) {
        this.username = username;
        this.id = id;
    }

    public Users(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
