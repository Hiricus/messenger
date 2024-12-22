package com.pavmaxdav.mymess.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private Integer id;
    private Integer authorId;
    private String content;
    private LocalDateTime sentAt;
    private AttachedResourceDTO attachedResource;

    // Конструкторы
    public MessageDTO() {}
    public MessageDTO(Integer id, Integer authorId, String content, LocalDateTime sentAt) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.sentAt = sentAt;
    }
    public MessageDTO(Integer id, Integer authorId, String content, LocalDateTime sentAt, AttachedResourceDTO attachedResource) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.sentAt = sentAt;
        this.attachedResource = attachedResource;
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public Integer getAuthorId() {
        return authorId;
    }
    public String getContent() {
        return content;
    }
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    public AttachedResourceDTO getAttachedResource() {
        if (attachedResource == null) {
            return null;
        }
        return attachedResource;
    }

    // Сеттеры
    public void setId(Integer id) {
        this.id = id;
    }
    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    public void setAttachedResource(AttachedResourceDTO attachedResource) {
        this.attachedResource = attachedResource;
    }


    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", content='" + content + '\'' +
                ", sentAt=" + sentAt +
                ", attachedResource=" + attachedResource +
                '}';
    }
}
