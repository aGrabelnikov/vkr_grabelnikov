package com.example.newanimals.view;

import com.example.newanimals.db.ChatData;

import java.util.List;

public interface ChatsView {
    void getChats(List<ChatData> chats);
    void errorMsg(String msg);
}
