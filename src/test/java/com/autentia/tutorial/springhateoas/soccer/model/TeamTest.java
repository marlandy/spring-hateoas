package com.autentia.tutorial.springhateoas.soccer.model;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TeamTest {

    private Team team;

    @Before
    public void init() {
        this.team = new Team();
    }

    @Test
    public void shouldSetAllAttributesSuccessfully() {
        team.setId(18);
        team.setFoundationYear(1981);
        team.setName("Team A");
        team.setRankingPosition(3);
        final List<PlayerShortInfo> players = createPlayerList();
        team.setPlayers(players);

        assertEquals(18, team.getId());
        assertEquals(1981, team.getFoundationYear());
        assertEquals("Team A", team.getName());
        assertEquals(3, team.getRankingPosition());
        assertEquals(players, team.getPlayers());
    }

    @Test
    public void shoudReturnTeamAsString() {
        team.setId(3);
        team.setFoundationYear(1902);
        team.setName("Real Madrid C.F");
        team.setRankingPosition(1);
        team.setStadium(new StadiumShortInfo("Estadio 1"));
        final List<PlayerShortInfo> players = createPlayerList();
        team.setPlayers(players);

        final StringBuilder expectedString = new StringBuilder();
        expectedString.append("Team{id=").append(3).
                append(", name='Real Madrid C.F', foundationYear=1902, rankingPosition=1, stadium=StadiumShortInfo{name='Estadio 1'}, players={");
        for (int i = 0; i < players.size(); i++) {
            if (i > 0) {
                expectedString.append(", ");
            }
            expectedString.append(players.get(i));
        }
        expectedString.append("}}");

        assertEquals(expectedString.toString(), team.toString());

    }

    private List<PlayerShortInfo> createPlayerList() {
        final List<PlayerShortInfo> players = new ArrayList<>();
        players.add(new PlayerShortInfo("CR7"));
        players.add(new PlayerShortInfo("Maradona"));
        players.add(new PlayerShortInfo("Zidane"));
        return players;
    }

}
