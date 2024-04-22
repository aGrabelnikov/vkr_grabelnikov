package com.example.newanimals.db;

public class UserData {
    public String name;
    public String phone;
    public String login;
    public String image;

    public UserData() {}

    public UserData(String name, String phone, String login, String image) {
        this.name = name;
        this.phone = phone;
        this.login = login;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
