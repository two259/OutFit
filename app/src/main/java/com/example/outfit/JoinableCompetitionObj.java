package com.example.outfit;

public class JoinableCompetitionObj {
    private String competitionName;
    private String competitionStartDate;
    private String competitionEndDate;
    private int competitionID;

    public JoinableCompetitionObj(String name, String start, String end, int id){
        this.competitionName = name;
        this.competitionStartDate = start;
        this.competitionEndDate = end;
        this.competitionID = id;
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

    public int getCompetitionID() {
        return competitionID;
    }
}
