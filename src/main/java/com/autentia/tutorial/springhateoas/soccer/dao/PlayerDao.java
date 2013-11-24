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
     * Devuelve el jugador que coincida con el id
     *
     * @param id identificador del jugador
     * @return jugador
     */
    Player getById(int id);

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
