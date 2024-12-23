package com.pavmaxdav.mymess.dto;

import com.pavmaxdav.mymess.entity.attached.ResourceType;

public class CreateMessageDTO {
    private Integer userId;
    private Integer chatId;
    private String content;

    private String resourceName;
    private String resource;
    private ResourceType resourceType;


    // Конструкторы

    public CreateMessageDTO() {}
    public CreateMessageDTO(Integer userId, Integer chatId, String content) {
        this.userId = userId;
        this.chatId = chatId;
        this.content = content;
    }
    public CreateMessageDTO(Integer userId, Integer chatId, String content, String resourceName, String resource, ResourceType resourceType) {
        this.userId = userId;
        this.chatId = chatId;
        this.content = content;

        this.resourceName = resourceName;
        this.resource = resource;
        this.resourceType = resourceType;
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

    public String getResourceName() {
        if (resource == null) {
            return null;
        }
        return resourceName;
    }
    public String getResource() {
        if (resource == null) {
            return null;
        }
        return resource;
    }
    public ResourceType getResourceType() {
        if (resourceType == null) {
            return null;
        }
        return resourceType;
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
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    public void setResource(String resource) {
        this.resource = resource;
    }
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }


    @Override
    public String toString() {
        return "CreateMessageDTO{" +
                "userId=" + userId +
                ", chatId=" + chatId +
                ", content='" + content + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", resource='" + resource + '\'' +
                ", resourceType=" + resourceType +
                '}';
    }
}
