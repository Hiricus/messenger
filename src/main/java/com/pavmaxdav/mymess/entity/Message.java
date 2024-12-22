package com.pavmaxdav.mymess.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    //todo
    // Сделать миграцию для того, чтобы при удалении пользователя не удалялись его сообщения
    @ManyToOne(optional = true)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_message_author", value = ConstraintMode.CONSTRAINT))
    private User author;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private AttachedResource attachedResource;


    // Конструкторы
    public Message() {}
    public Message(String content, User author, Chat chat) {
        this.content = content;
        this.author = author;
        this.chat = chat;

        this.sentAt = LocalDateTime.now();
    }
    public Message(String content, User author, Chat chat, AttachedResource attachedResource) {
        this.content = content;
        this.author = author;
        this.chat = chat;
        this.attachedResource = attachedResource;

        this.sentAt = LocalDateTime.now();
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    public User getAuthor() {
        return author;
    }
    public Chat getChat() {
        return chat;
    }

    // TODO Сделать ресурс в БД
    public AttachedResource getAttachedResource() {
        if (attachedResource == null) {
            return null;
        }
        return attachedResource;
    }


    // Сеттеры
    public void setContent(String content) {
        this.content = content;
    }
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public void setChat(Chat chat) {
        this.chat = chat;
    }


    // Misc

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sentAt=" + sentAt +
                '}';
    }
}
