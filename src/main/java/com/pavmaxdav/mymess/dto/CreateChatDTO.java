package com.pavmaxdav.mymess.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class CreateChatDTO {
    private String chatName;

    @JsonProperty("isGroup")
    private boolean isGroup;
    ArrayList<String> userList;

    // Конструкторы
    public CreateChatDTO() {}
    public CreateChatDTO(String chatName, boolean isGroup, ArrayList<String> userList) {
        this.chatName = chatName;
        this.isGroup = isGroup;
        this.userList = userList;
    }

    // Геттеры
    public String getChatName() {
        return chatName;
    }
    public boolean isGroup() {
        return isGroup;
    }
    public ArrayList<String> getUserList() {
        return userList;
    }

    // Сеттеры
    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
    public void setGroup(boolean group) {
        isGroup = group;
    }
    public void setUserList(ArrayList<String> userList) {
        this.userList = userList;
    }


    @Override
    public String toString() {
        return "CreateChatDTO{" +
                "chatName='" + chatName + '\'' +
                ", isGroup=" + isGroup +
                ", userList=" + userList +
                '}';
    }
}
