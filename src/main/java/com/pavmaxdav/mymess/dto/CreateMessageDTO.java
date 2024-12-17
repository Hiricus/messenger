package com.pavmaxdav.mymess.dto;

import com.pavmaxdav.mymess.entity.AttachedResource;

public class CreateMessageDTO {
    private Integer userId;
    private Integer chatId;
    private String content;
    private AttachedResource attachedResource = null;

    // Конструкторы
    public CreateMessageDTO(Integer userId, Integer chatId, String content) {
        this.userId = userId;
        this.chatId = chatId;
        this.content = content;
    }

    // Геттеры
    public Integer getUserId() {
        return userId;
    }
    public Integer getChatId() {
        return chatId;
    }
    public String getContent() {
        return content;
    }
    public AttachedResource getAttachedResource() {
        return attachedResource;
    }

    // Сеттеры
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setAttachedResource(AttachedResource attachedResource) {
        this.attachedResource = attachedResource;
    }
}
