package com.autentia.tutorial.springhateoas.soccer.dao;

import com.autentia.tutorial.springhateoas.soccer.model.Player;

import java.util.List;

public interface PlayerDao {

    /**
     * Devuelve todos los jugadores
     *
     * @return listado de jugadores
     */
    List<Player> getAll();

    /**
     * Devuelve un jugador por id jugador e id equip
     *
     * @param playerId identificador del jugador
     * @param teamId identificador del equipo en el que juega
     * @return jugador
     */
    Player getById(int playerId, int teamId);

    /**
     * Devuelve los jugadores cuyo equipo coincida con el parámetro
     *
     * @param teamId identificador del equipo del jugador
     * @return jugador
     */
    List<Player> getByTeamId(int teamId);

    /**
     * Da de alta un jugador
     *
     * @param player a dar de alta
     * @return identificador del jugador creado
     */
    int persist(Player player);

    /**
     * Elimina un jugador
     *
     * @param playerId id del jugador a borrar
     */
    void delete(int playerId);
}
