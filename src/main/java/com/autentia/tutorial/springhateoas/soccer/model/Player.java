package com.autentia.tutorial.springhateoas.soccer.model;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"playerId", "name", "goals", "age", "country", "teamId"})
public class Player extends ResourceSupport {

    private int playerId;

    private String name;

    private int goals;

    private int age;

    private String country;

    private int teamId;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }

        final Player other = (Player) o;
        return new EqualsBuilder().append(this.playerId, other.getPlayerId()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.playerId).hashCode();
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + playerId +
                ", name='" + name + '\'' +
                ", goals=" + goals +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", teamId=" + teamId +
                '}';
    }
}
