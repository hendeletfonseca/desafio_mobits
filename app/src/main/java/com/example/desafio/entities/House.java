package com.example.desafio.entities;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
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
    public House(String url, String name, String region, String coatOfArms, String words, List<String> titles, List<String> seats, String currentLord, String heir, String overlord, String founded, String founder, String diedOut, List<String> ancestralWeapons, List<String> cadetBranches, List<String> swornMembers) {
        this.url = url;
        this.name = name;
        this.region = region;
        this.coatOfArms = coatOfArms;
        this.words = words;
        this.titles = titles;
        this.seats = seats;
        this.currentLord = currentLord;
        this.heir = heir;
        this.overlord = overlord;
        this.founded = founded;
        this.founder = founder;
        this.diedOut = diedOut;
        this.ancestralWeapons = ancestralWeapons;
        this.cadetBranches = cadetBranches;
        this.swornMembers = swornMembers;
    }

    public House(JSONObject dataObj) {

        try {
            this.url = dataObj.getString("url");
            this.name = dataObj.getString("name");
            this.region = dataObj.getString("region");
            this.coatOfArms = dataObj.getString("coatOfArms");
            this.words = dataObj.getString("words");
            JSONArray titlesArray = dataObj.getJSONArray("titles");
            this.titles = new ArrayList<>();
            for (int j = 0; j < titlesArray.length(); j++) {
                titles.add(titlesArray.getString(j));
            }
            JSONArray seatsArray = dataObj.getJSONArray("seats");
            this.seats = new ArrayList<>();
            for (int j = 0; j < seatsArray.length(); j++) {
                seats.add(seatsArray.getString(j));
            }
            this.currentLord = dataObj.getString("currentLord");
            this.heir = dataObj.getString("heir");
            this.overlord = dataObj.getString("overlord");
            this.founded = dataObj.getString("founded");
            this.founder = dataObj.getString("founder");
            this.diedOut = dataObj.getString("diedOut");
            JSONArray ancestralWeaponsArray = dataObj.getJSONArray("ancestralWeapons");
            this.ancestralWeapons = new ArrayList<>();
            for (int j = 0; j < ancestralWeaponsArray.length(); j++) {
                ancestralWeapons.add(ancestralWeaponsArray.getString(j));
            }
            JSONArray cadetBranchesArray = dataObj.getJSONArray("cadetBranches");
            this.cadetBranches = new ArrayList<>();
            for (int j = 0; j < cadetBranchesArray.length(); j++) {
                cadetBranches.add(cadetBranchesArray.getString(j));
            }
            JSONArray swornMembersArray = dataObj.getJSONArray("swornMembers");
            this.swornMembers = new ArrayList<>();
            for (int j = 0; j < swornMembersArray.length(); j++) {
                swornMembers.add(swornMembersArray.getString(j));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

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
