package com.pavmaxdav.mymess.entity;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login", nullable = false, unique = true)
    @NonNull
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "last_online")
    private LocalDateTime lastOnline;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private UserData userData;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_chats", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"))
    private Set<Chat> chats;


    // Конструкторы
    public User() {}
    public User(String login, String password) {
        if (login == null) {
            login = "";
        }
        this.login = login;

        if (password == null) {
            password = "";
        }
        this.password = password;
    }


    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public LocalDateTime getLastOnline() {
        return lastOnline;
    }
    public UserData getUserData() {
        return userData;
    }
    public Set<Chat> getChats() {
        return chats;
    }


    // Сеттеры
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setLastOnline(LocalDateTime lastOnline) {
        this.lastOnline = lastOnline;
    }
    public void setUserData(UserData userData) {
        this.userData = userData;
    }


    // Добавление/удаление
    public void addToChat(Chat chat) {
        this.chats.add(chat);
        chat.getUsers().add(this);
    }
    public void removeFromChat(Chat chat) {
        this.chats.remove(chat);
        chat.getUsers().remove(this);
    }

//    @PreRemove
//    private void removeChats() {
//        this.chats.clear();
//    }
}
