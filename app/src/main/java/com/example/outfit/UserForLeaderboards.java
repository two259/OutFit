package com.example.outfit;

public class UserForLeaderboards {

    private int score;
    private String userName;

    public UserForLeaderboards(String user, int sc){
        this.userName = user;
        this.score = sc;
    }

    public int getScore() {
        return score;
    }

    public String getUserName() {
        return userName;
    }
}
