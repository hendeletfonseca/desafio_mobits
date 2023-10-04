package com.example.desafio.entities;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Character implements Serializable {
    private String url;
    private String name;
    private String gender;
    private String culture;
    private String born;
    private String died;
    private List<String> titles;
    private List<String> aliases;
    private String father;
    private String mother;
    private String spouse;
    private List<String> allegiances;
    private List<String> books;
    private List<String> povBooks;
    private List<String> tvSeries;
    private List<String> playedBy;

    public Character(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getCulture() {
        return culture;
    }

    public String getBorn() {
        return born;
    }

    public String getDied() {
        return died;
    }

    public List<String> getTitles() {
        return titles;
    }

    public String getStringTitles() {
        StringBuilder sb = new StringBuilder();
        for (String title: titles) {
            sb.append(title).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public List<String> getAllegiances() {
        return allegiances;
    }

    public List<String> getBooks() {
        return books;
    }

    public List<String> getPovBooks() {
        return povBooks;
    }

    public List<String> getTvSeries() {
        return tvSeries;
    }

    public List<String> getPlayedBy() {
        return playedBy;
    }
    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
