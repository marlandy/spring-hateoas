package com.autentia.tutorial.springhateoas.soccer.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.List;

public class Team {

    private int id;

    private String name;

    private int foundationYear;

    private int rankingPosition;

    private StadiumShortInfo stadium;

    private List<PlayerShortInfo> players;

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

    public StadiumShortInfo getStadium() {
        return stadium;
    }

    public void setStadium(StadiumShortInfo stadium) {
        this.stadium = stadium;
    }

    public List<PlayerShortInfo> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerShortInfo> players) {
        this.players = players;
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
        return new EqualsBuilder().append(this.id, other.getId()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Team{id=").append(id).append(", name='").append(name).
                append("', foundationYear=").append(foundationYear).append(", rankingPosition=").
                append(rankingPosition).append(", stadium=").append(stadium).append(", players={");
        if (players != null) {
            for (int i = 0; i < players.size(); i++) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(players.get(i));
            }
        }
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }
}
