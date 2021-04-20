package com.example.outfit;

public class UserCompetitionsObj {

    private String competitionName;
    private String competitionStartDate;
    private String competitionEndDate;

    public UserCompetitionsObj(String name, String start, String end){
        this.competitionName = name;
        this.competitionStartDate = start;
        this.competitionEndDate = end;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public String getCompetitionStartDate() {
        return competitionStartDate;
    }

    public String getCompetitionEndDate() {
        return competitionEndDate;
    }
}
