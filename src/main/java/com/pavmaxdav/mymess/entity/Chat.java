package com.pavmaxdav.mymess.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_group")
    private boolean isGroup;

    @Column(name = "created_at")
    private LocalDateTime lastActive;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "author_id")
    private Set<Message> messages = new HashSet<>();

    @ManyToMany(mappedBy = "chats", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<User> users = new HashSet<>();


    // Конструкторы
    public Chat() {}
    public Chat(String name, boolean isGroup) {
        this.name = name;
        this.isGroup = isGroup;
        lastActive = LocalDateTime.now();
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
    public Set<Message> getMessages() {
        return messages;
    }
    public Set<User> getUsers() {
        return users;
    }


    // Сеттеры
    public void setName(String name) {
        this.name = name;
    }
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

//    @PreRemove
//    private void removeUsers() {
//        this.users.clear();
//    }
}
