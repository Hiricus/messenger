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

    @Column(name = "last_active")
    private LocalDateTime lastActive;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
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
    public int getUserCount() {
        return users.size();
    }


    // Сеттеры
    public void setName(String name) {
        this.name = name;
    }
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    // Добавлялки/удалялки
    public void addMessage(Message message) {
        this.messages.add(message);
        this.lastActive = message.getSentAt();
    }
    public void removeMessage(Message message) {
        this.messages.remove(message);
    }
    public void addUser(User user) {
        this.users.add(user);
    }
    public void removeUser(User user) {
        this.users.remove(user);
    }


    // Разрыв связей для удаления из бд
    @PreRemove
    private void removeUserAssociations() {
        // Удаляем чат из списков его участников
        for (User user : this.getUsers()) {
            user.getChats().remove(this);
        }
        this.getUsers().clear();
    }
}
