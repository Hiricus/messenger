package com.pavmaxdav.mymess.entity;

import jakarta.persistence.*;

@Entity
public class UserData {
    @Id
    @Column(name = "user_id")
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "settings")
    private String settings = "";

    @Column(name = "about_me")
    private String aboutMe = "";

    @Lob
    @Column(name = "avatar", columnDefinition = "MEDIUMBLOB")
    private byte[] avatarImage = new byte[0];


    // Конструкторы
    public UserData() {}
    public UserData(User user) {
        this.user = user;
    }
    public UserData(User user, String settings, String aboutMe) {
        this.user = user;
        this.settings = settings;
        this.aboutMe = aboutMe;
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public String getSettings() {
        return settings;
    }
    public String getAboutMe() {
        return aboutMe;
    }
    public byte[] getAvatarImage() {
        return avatarImage;
    }

    // Сеттеры
    public void setSettings(String settings) {
        this.settings = settings;
    }
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
    public void setAvatarImage(byte[] avatarImage) {
        this.avatarImage = avatarImage;
    }
}
