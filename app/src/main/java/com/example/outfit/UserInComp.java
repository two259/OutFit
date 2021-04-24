package com.example.outfit;

import java.util.ArrayList;
import java.util.List;

public class UserInComp {

    private String userID;
    List<Competition> userCompetitions;

    public UserInComp(String user){
        this.userID = user;
        userCompetitions = new ArrayList<Competition>();
    }

    public String getUserID() {
        return userID;
    }

    public List<Competition> getUserCompetitionsList(){
        return userCompetitions;
    }
}
