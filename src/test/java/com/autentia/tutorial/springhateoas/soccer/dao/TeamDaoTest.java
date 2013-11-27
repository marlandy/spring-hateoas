package com.autentia.tutorial.springhateoas.soccer.dao;

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
        assertEquals(4, teams.size());
    }

    @Test
    public void shouldReturnATeamById() {
        final Team team = teamDao.getById(5000);
        assertNotNull(team);
        assertEquals(5000, team.getTeamId());
        assertEquals("Real Madrid C.F.", team.getName());
        assertEquals(1902, team.getFoundationYear());
        assertEquals(1, team.getRankingPosition());

    }

    @Test
    public void shouldReturnNullIfTeamDoesntExists() {
        int unexistingTeamId = 333;
        assertNull(teamDao.getById(unexistingTeamId));
    }

    @Test
    public void shouldPersistATeam() {
        final Team team = new Team();
        team.setName("Valencia C.F.");
        team.setFoundationYear(1917);
        team.setRankingPosition(7);

        int teamId = teamDao.persist(team);
        final Team teamFromDB = teamDao.getById(teamId);
        assertEquals(teamId, teamFromDB.getTeamId());
        assertEquals(team.getName(), teamFromDB.getName());
        assertEquals(team.getFoundationYear(), teamFromDB.getFoundationYear());
        assertEquals(team.getRankingPosition(), teamFromDB.getRankingPosition());

        deleteTeamAndValidateDeletion(teamFromDB);
    }

    private void deleteTeamAndValidateDeletion(Team team) {
        teamDao.delete(team.getTeamId());
        assertNull(teamDao.getById(team.getTeamId()));
        assertEquals(4, teamDao.getAll().size());
    }

}
