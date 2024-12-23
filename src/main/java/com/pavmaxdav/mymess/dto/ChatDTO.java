package com.pavmaxdav.mymess.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatDTO {
    private Integer id;
    private String name;
    private boolean isGroup;
    private LocalDateTime lastActive;
    private ArrayList<UserDTO> userDTOS;
    private MessageDTO lastMessage = null;

    // Конструктор
    public ChatDTO(Integer id, String name, boolean isGroup, LocalDateTime lastActive) {
        this.id = id;
        this.name = name;
        this.isGroup = isGroup;
        this.lastActive = lastActive;
    }
    public ChatDTO(Integer id, String name, boolean isGroup, LocalDateTime lastActive, ArrayList<UserDTO> userDTOS, MessageDTO lastMessage) {
        this.id = id;
        this.name = name;
        this.isGroup = isGroup;
        this.lastActive = lastActive;
        this.userDTOS = userDTOS;
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public boolean isGroup() {
        return isGroup;
    }
    public LocalDateTime getLastActive() {
        return lastActive;
    }
    public ArrayList<UserDTO> getUserDTOS() {
        return userDTOS;
    }
    public MessageDTO getLastMessage() {
        if (lastMessage == null) {
            return null;
        }
        return lastMessage;
    }

    // Сеттеры
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGroup(boolean group) {
        isGroup = group;
    }
    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }
    public void setUserDTOS(ArrayList<UserDTO> userDTOS) {
        this.userDTOS = userDTOS;
    }
    public void setLastMessage(MessageDTO lastMessage) {
        this.lastMessage = lastMessage;
    }


    @Override
    public String toString() {
        return "ChatDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isGroup=" + isGroup +
                ", lastActive=" + lastActive +
                ", userDTOS=" + userDTOS +
                ", lastMessage=" + lastMessage +
                '}';
    }
}
