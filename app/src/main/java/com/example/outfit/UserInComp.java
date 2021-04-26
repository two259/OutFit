package com.example.outfit;

import java.util.ArrayList;
import java.util.List;

public class UserInComp {

    private String userID;
    private int userScore;

    public UserInComp(String user, int sc){
        this.userID = user;
        this.userScore = sc;
    }

    public String getUserID() {
        return userID;
    }

    public int getScore(){
        return userScore;
    }
}
