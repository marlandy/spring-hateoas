package com.autentia.tutorial.springhateoas.soccer.dao;

import com.autentia.tutorial.springhateoas.soccer.model.Player;
import com.autentia.tutorial.springhateoas.soccer.model.TeamShortInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PlayerDaoTest {

    @Autowired
    private PlayerDao playerDao;

    @Test
    public void shouldReturnAllPlayers() {
        final List<Player> players = playerDao.getAll();

        assertNotNull(players);
        assertEquals(3, players.size());
    }

    @Test
    public void shoudReturnAPlayerById() {
        final List<Player> players = playerDao.getAll();

        assertNotNull(players);
        assertFalse(players.isEmpty());

        final Player player = players.get(0);
        final Player playerById = playerDao.getById(player.getId());

        assertNotNull(playerById);
        assertEquals(player.getId(), playerById.getId());
        assertEquals(player.getAge(), playerById.getAge());
        assertEquals(player.getGoals(), playerById.getGoals());
        assertEquals(player.getCountry(), playerById.getCountry());
        assertEquals(player.getName(), playerById.getName());
        assertNotNull(playerById.getCurrentTeam());
        assertEquals(player.getCurrentTeam(), playerById.getCurrentTeam());
    }

    @Test
    public void shouldReturnNullIfPlayerDoesntExists() {
        int unexistingPlayerId = 222;
        assertNull(playerDao.getById(unexistingPlayerId));
    }

    @Test
    public void shouldPersistAPlayer() {
        final Player player = new Player();
        player.setAge(22);
        player.setCountry("Italia");
        player.setGoals(3);
        player.setName("Nuevo jugador");
        player.setCurrentTeam(new TeamShortInfo("F.C. Barcelona"));

        int newPlayerId = playerDao.persist(player);

        final Player playerFromDB = playerDao.getById(newPlayerId);
        assertNotNull(playerFromDB);
        assertEquals(player.getAge(), playerFromDB.getAge());
        assertEquals(player.getCountry(), playerFromDB.getCountry());
        assertEquals(player.getGoals(), playerFromDB.getGoals());
        assertEquals(player.getName(), playerFromDB.getName());
        assertEquals(player.getCurrentTeam(), playerFromDB.getCurrentTeam());

        deletePlayerAndValidateDeletion(playerFromDB);
    }

    @Test
    public void shouldThrownExceptionIfTeamNameIsNotValid() {
        final Player player = new Player();
        player.setAge(22);
        player.setCountry("Italia");
        player.setGoals(3);
        player.setName("Nuevo jugador");
        player.setCurrentTeam(new TeamShortInfo("EQUIPO INEXISTENTE"));

        try {
            playerDao.persist(player);
            fail("El test debió fallar ya que no existe el equipo del jugador");
        } catch (IllegalArgumentException iae) {
            assertEquals("No existe un equipo con nombre EQUIPO INEXISTENTE", iae.getMessage());
        }
    }

    private void deletePlayerAndValidateDeletion(Player player) {
        playerDao.delete(player.getId());
        assertNull(playerDao.getById(player.getId()));
        assertEquals(3, playerDao.getAll().size());
    }
}
