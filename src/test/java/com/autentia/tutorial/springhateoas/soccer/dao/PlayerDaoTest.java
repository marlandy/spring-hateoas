package com.autentia.tutorial.springhateoas.soccer.dao;

import com.autentia.tutorial.springhateoas.soccer.model.Player;
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
        final Player playerById = playerDao.getById(player.getPlayerId(), player.getTeamId());

        assertNotNull(playerById);
        assertEquals(player.getPlayerId(), playerById.getPlayerId());
        assertEquals(player.getAge(), playerById.getAge());
        assertEquals(player.getGoals(), playerById.getGoals());
        assertEquals(player.getCountry(), playerById.getCountry());
        assertEquals(player.getName(), playerById.getName());
        assertEquals(player.getTeamId(), playerById.getTeamId());
    }

    @Test
    public void shouldReturnNullIfPlayerIdDoesntExists() {
        int unexistingPlayerId = 222;
        int existingTeam = 5000;
        assertNull(playerDao.getById(unexistingPlayerId, existingTeam));
    }

    @Test
    public void shouldReturnNullIfTeamIdDoesntExists() {
        int existingPlayerId = 7000;
        int unexistingTeam = 999;
        assertNull(playerDao.getById(existingPlayerId, unexistingTeam));
    }

    @Test
    public void shouldReturnAllPlayersForATeam() {
        final List<Player> players = playerDao.getByTeamId(5000);
        assertNotNull(players);
        assertEquals(3, players.size());
    }

    @Test
    public void shouldPersistAPlayer() {
        final Player player = new Player();
        player.setAge(22);
        player.setCountry("Italia");
        player.setGoals(3);
        player.setName("Nuevo jugador");
        player.setTeamId(5000);

        int newPlayerId = playerDao.persist(player);

        final Player playerFromDB = playerDao.getById(newPlayerId, player.getTeamId());
        assertNotNull(playerFromDB);
        assertEquals(player.getAge(), playerFromDB.getAge());
        assertEquals(player.getCountry(), playerFromDB.getCountry());
        assertEquals(player.getGoals(), playerFromDB.getGoals());
        assertEquals(player.getName(), playerFromDB.getName());
        assertEquals(player.getTeamId(), playerFromDB.getTeamId());

        deletePlayerAndValidateDeletion(playerFromDB);
    }

    @Test
    public void shouldThrownExceptionIfTeamIdIsNotValid() {
        int unexistingTeamId = 999;
        final Player player = new Player();
        player.setAge(22);
        player.setCountry("Italia");
        player.setGoals(3);
        player.setName("Nuevo jugador");
        player.setTeamId(unexistingTeamId);

        try {
            playerDao.persist(player);
            fail("El test debió fallar ya que no existe el equipo");
        } catch (IllegalArgumentException iae) {
            assertEquals("No existe un equipo con id " + unexistingTeamId, iae.getMessage());
        }
    }

    private void deletePlayerAndValidateDeletion(Player player) {
        playerDao.delete(player.getPlayerId());
        assertNull(playerDao.getById(player.getPlayerId(), player.getTeamId()));
        assertEquals(3, playerDao.getAll().size());
    }
}
