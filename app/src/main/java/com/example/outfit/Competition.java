package com.example.outfit;

public class Competition {

    private String competitionName;
    private String compDescription;
    private String startDate;
    private String endDate;
    private String competitionType;
    private int competitionID;

    public Competition(String name, String desc, String start, String end, String type, int compID){
        this.competitionName = name;
        this.compDescription = desc;
        this.startDate = start;
        this.endDate = end;
        this.competitionType = type;
        this.competitionID = compID;
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
}
