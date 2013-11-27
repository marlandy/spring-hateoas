package com.autentia.tutorial.springhateoas.soccer.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "stadiumId", "name", "capacity", "city", "teamId"})
public class Stadium extends ResourceSupport {

    private int stadiumId;

    private String name;

    private int capacity;

    private String city;

    private int teamId;

    public int getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(int id) {
        this.stadiumId = id;
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
        if (!(o instanceof Stadium)) {
            return false;
        }

        final Stadium other = (Stadium) o;
        return new EqualsBuilder().append(this.stadiumId, other.getStadiumId()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.stadiumId).hashCode();
    }

    @Override
    public String toString() {
        return "Stadium{" +
                "id=" + stadiumId +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", city='" + city + '\'' +
                ", teamId=" + teamId +
                '}';
    }
}
