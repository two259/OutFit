package com.example.outfit;

import java.util.ArrayList;
import java.util.List;

public class Competition {

    private String competitionName;
    private String compDescription;
    private String startDate;
    private String endDate;
    private String competitionType;
    private int competitionID;
    private List<UserInComp> userList;

    /**
     * This class represents each competition, and its contents.
     */
    public Competition(String name, String desc, String start, String end, String type, int compID){
        this.competitionName = name;
        this.compDescription = desc;
        this.startDate = start;
        this.endDate = end;
        this.competitionType = type;
        this.competitionID = compID;
        userList = new ArrayList<UserInComp>();
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public String getCompDescription() {
        return compDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCompetitionType() {
        return competitionType;
    }

    public int getCompetitionID() {
        return competitionID;
    }

    public void setCompetitionID(int newID){
        this.competitionID = newID;
    }

    public List<UserInComp> getUserList(){
        return userList;
    }

    public void setUserList(List<UserInComp> newList){
        this.userList = newList;
    }
}
