package com.autentia.tutorial.springhateoas.soccer.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Stadium {

    private int id;

    private String name;

    private int capacity;

    private String city;

    private TeamShortInfo team;

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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public TeamShortInfo getTeam() {
        return team;
    }

    public void setTeam(TeamShortInfo team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stadium)) {
            return false;
        }

        final Stadium other = (Stadium) o;
        return new EqualsBuilder().append(this.id, other.getId()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).hashCode();
    }

    @Override
    public String toString() {
        return "Stadium{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", city='" + city + '\'' +
                ", team=" + team +
                '}';
    }
}
