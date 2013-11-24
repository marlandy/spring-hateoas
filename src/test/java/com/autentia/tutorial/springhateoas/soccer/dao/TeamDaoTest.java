package com.autentia.tutorial.springhateoas.soccer.dao;

import com.autentia.tutorial.springhateoas.soccer.model.PlayerShortInfo;
import com.autentia.tutorial.springhateoas.soccer.model.StadiumShortInfo;
import com.autentia.tutorial.springhateoas.soccer.model.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TeamDaoTest {

    @Autowired
    private TeamDao teamDao;

    @Test
    public void shouldReturnAllTeams() {
        final List<Team> teams = teamDao.getAll();
        assertNotNull(teams);
        assertEquals(3, teams.size());
    }

    @Test
    public void shouldReturnATeamById() {
        final Team team = teamDao.getById(5000);
        assertNotNull(team);
        assertEquals(5000, team.getId());
        assertEquals("Real Madrid C.F.", team.getName());
        assertEquals(1902, team.getFoundationYear());
        assertEquals(1, team.getRankingPosition());
        assertEquals("Santiago Bernabeu", team.getStadium().getName());

        final List<PlayerShortInfo> teamPlayers = team.getPlayers();
        assertNotNull(teamPlayers);
        assertEquals(3, teamPlayers.size());
        assertEquals("Cristiano Ronaldo", teamPlayers.get(0).getName());

    }

    @Test
    public void shouldReturnNullIfTeamDoesntExists() {
        int unexistingTeamId = 333;
        assertNull(teamDao.getById(unexistingTeamId));
    }

    @Test
    public void shouldPersistATeam() {
        final Team team = new Team();
        team.setName("Málaga C.F.");
        team.setFoundationYear(1948);
        team.setRankingPosition(7);
        team.setStadium(new StadiumShortInfo("La Rosaleda"));

        int teamId = teamDao.persist(team);
        final Team teamFromDB = teamDao.getById(teamId);
        assertEquals(teamId, teamFromDB.getId());
        assertEquals(team.getName(), teamFromDB.getName());
        assertEquals(team.getFoundationYear(), teamFromDB.getFoundationYear());
        assertEquals(team.getRankingPosition(), teamFromDB.getRankingPosition());
        assertEquals(team.getStadium(), teamFromDB.getStadium());

        deleteTeamAndValidateDeletion(teamFromDB);
    }

    @Test
    public void shouldThrownExceptionIfStadiumNameIsNotValid() {
        final Team team = new Team();
        team.setName("Real Madrid Castilla");
        team.setFoundationYear(1950);
        team.setRankingPosition(7);
        team.setStadium(new StadiumShortInfo("ESTADIO INEXISTENTE"));

        try {
            teamDao.persist(team);
            fail("El test debió fallar ya que no existe el estadio");
        } catch (IllegalArgumentException iae) {
            assertEquals("No existe un estadio con nombre ESTADIO INEXISTENTE", iae.getMessage());
        }
    }

    private void deleteTeamAndValidateDeletion(Team team) {
        teamDao.delete(team.getId());
        assertNull(teamDao.getById(team.getId()));
        assertEquals(3, teamDao.getAll().size());
    }

}
