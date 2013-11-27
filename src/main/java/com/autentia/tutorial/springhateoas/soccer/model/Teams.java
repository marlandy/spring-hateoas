package com.autentia.tutorial.springhateoas.soccer.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Teams {

    private List<Team> teams;

    private Teams() {
        this.teams = null;
    }

    public Teams(List<Team> teams) {
        this.teams = teams;
    }

    @XmlElement(name = "team")
    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
