package com.example.newanimals.view;

import com.example.newanimals.db.MessageData;

import java.util.List;

public interface MessageView {
    void getMessages(List<MessageData> messages);

    void errorMsg(String message);
    void successSend();
}
