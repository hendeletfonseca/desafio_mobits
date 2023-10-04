package com.example.desafio.entities;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class House implements Serializable {
    private String url;
    private String name;
    private String region;
    private String coatOfArms;
    private String words;
    private List<String> titles;
    private List<String> seats;
    private String currentLord;
    private String heir;
    private String overlord;
    private String founded;
    private String founder;
    private String diedOut;
    private List<String> ancestralWeapons;
    private List<String> cadetBranches;
    private List<String> swornMembers;

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCoatOfArms() {
        return coatOfArms;
    }

    public String getWords() {
        return words;
    }
    public String getWordsString() {
        if (words.equals("") || words.equals("null"))
            return "Sem frase!";
        return words;
    }

    public List<String> getTitles() {
        return titles;
    }
    public String getTitlesString() {
        StringBuilder titlesString = new StringBuilder();
        for (String title : titles) {
            titlesString.append(title).append(", ");
        }
        titlesString.delete(titlesString.length() - 2, titlesString.length());
        if (titlesString.toString().equals("") || titlesString.toString().equals("null"))
            return "Sem títulos!";

        return titlesString.toString();
    }

    public List<String> getSeats() {
        return seats;
    }

    public String getCurrentLord() {
        return currentLord;
    }

    public String getHeir() {
        return heir;
    }

    public String getOverlord() {
        return overlord;
    }

    public String getFounded() {
        return founded;
    }

    public String getFounder() {
        return founder;
    }

    public String getDiedOut() {
        return diedOut;
    }

    public List<String> getAncestralWeapons() {
        return ancestralWeapons;
    }

    public List<String> getCadetBranches() {
        return cadetBranches;
    }

    public List<String> getSwornMembers() {
        return swornMembers;
    }
    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
