package com.example.newanimals.db;

import java.util.List;

public class ChatData {
    public String login;
    public String name;
    public List<MessageData> message;

    public ChatData() {
    }

    public ChatData(String login, String name, List<MessageData> message) {
        this.login = login;
        this.name = name;
        this.message = message;
    }

    public List<MessageData> getMessage() {
        return message;
    }

    public void setMessage(List<MessageData> message) {
        this.message = message;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
