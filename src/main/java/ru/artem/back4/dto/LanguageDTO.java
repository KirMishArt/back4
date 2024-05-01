package ru.artem.back4.dto;

import java.util.List;

public class LanguageDTO {
    private String name;
    private String surname;
    private String language;

    public LanguageDTO() {
    }

    public LanguageDTO(String name, String surname, String language) {
        this.name = name;
        this.surname = surname;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
