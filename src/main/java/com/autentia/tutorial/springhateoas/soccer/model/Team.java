package com.autentia.tutorial.springhateoas.soccer.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "teamId", "name", "foundationYear", "rankingPosition"})
public class Team extends ResourceSupport {

    private int teamId;

    private String name;

    private int foundationYear;

    private int rankingPosition;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int id) {
        this.teamId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoundationYear() {
        return foundationYear;
    }

    public void setFoundationYear(int foundationYear) {
        this.foundationYear = foundationYear;
    }

    public int getRankingPosition() {
        return rankingPosition;
    }

    public void setRankingPosition(int rankingPosition) {
        this.rankingPosition = rankingPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }

        final Team other = (Team) o;
        return new EqualsBuilder().append(this.teamId, other.getTeamId()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.teamId).hashCode();
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + teamId +
                ", name='" + name + '\'' +
                ", foundationYear=" + foundationYear +
                ", rankingPosition=" + rankingPosition +
                '}';
    }
}
