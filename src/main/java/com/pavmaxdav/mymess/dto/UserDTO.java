package com.pavmaxdav.mymess.dto;

import com.pavmaxdav.mymess.entity.attached.Settings;

import java.util.Arrays;

public class UserDTO {
    private Integer id;
    private String login;
    private String avatarImage = "";
    private Settings settings = new Settings();

    // Конструкторы
    public UserDTO(Integer id, String login) {
        this.id = id;
        this.login = login;
    }
    public UserDTO(Integer id, String login, String avatarImage) {
        this.id = id;
        this.login = login;
        this.avatarImage = avatarImage;
    }
    public UserDTO(Integer id, String login, String avatarImage, Settings settings) {
        this.id = id;
        this.login = login;
        this.avatarImage = avatarImage;
        this.settings = settings;
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getAvatarImage() {
        return avatarImage;
    }
    public Settings getSettings() {
        return settings;
    }

    // Сеттеры
    public void setId(Integer id) {
        this.id = id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", avatarImage=" + avatarImage +
                '}';
    }
}
