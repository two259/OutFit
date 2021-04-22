package com.example.outfit;

public class JoinableCompetitionObj {
    private String competitionName;
    private String competitionStartDate;
    private String competitionEndDate;

    public JoinableCompetitionObj(String name, String start, String end){
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
