package com.pavmaxdav.mymess.entity.attached;

public class Settings {
    Theme theme = Theme.LIGHT;
    String other = "";

    // Конструкторы
    public Settings() {}
    public Settings(Theme theme) {
        this.theme = theme;
    }
    public Settings(Theme theme, String other) {
        this.theme = theme;
        this.other = other;
    }

    // Геттеры
    public Theme getTheme() {
        return theme;
    }
    public String getOther() {
        return other;
    }

    // Сеттеры
    public void setTheme(Theme theme) {
        this.theme = theme;
    }
    public void setOther(String other) {
        this.other = other;
    }
}
