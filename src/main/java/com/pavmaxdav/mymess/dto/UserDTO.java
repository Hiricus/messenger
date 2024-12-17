package com.pavmaxdav.mymess.dto;

import java.util.Arrays;

public class UserDTO {
    private Integer id;
    private String login;
    private byte[] avatarImage = new byte[0];

    // Конструкторы
    public UserDTO(Integer id, String login) {
        this.id = id;
        this.login = login;
    }
    public UserDTO(Integer id, String login, byte[] avatarImage) {
        this.id = id;
        this.login = login;
        this.avatarImage = avatarImage;
    }

    // Геттеры
    public Integer getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public byte[] getAvatarImage() {
        return avatarImage;
    }

    // Сеттеры
    public void setId(Integer id) {
        this.id = id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setAvatarImage(byte[] avatarImage) {
        this.avatarImage = avatarImage;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", avatarImage=" + Arrays.toString(avatarImage) +
                '}';
    }
}
