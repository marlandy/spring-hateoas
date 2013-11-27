package com.autentia.tutorial.springhateoas.soccer.model;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "id", "name", "goals", "age", "country", "currentTeam"})
public class Player {

    private int id;

    private String name;

    private int goals;

    private int age;

    private String country;

    private TeamShortInfo currentTeam;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamShortInfo getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(TeamShortInfo currentTeam) {
        this.currentTeam = currentTeam;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }

        final Player other = (Player) o;
        return new EqualsBuilder().append(this.id, other.getId()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).hashCode();
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentTeam=" + currentTeam +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", goals=" + goals +
                '}';
    }
}
