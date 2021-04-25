package com.example.outfit;

public class LeaderboardItem {

    private String username;
    private int score;

    public LeaderboardItem(String user, int sc){
        this.username = user;
        this.score = sc;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}
