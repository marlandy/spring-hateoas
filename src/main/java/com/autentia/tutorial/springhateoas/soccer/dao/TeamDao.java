package com.autentia.tutorial.springhateoas.soccer.dao;

import com.autentia.tutorial.springhateoas.soccer.model.Team;

import java.util.List;

public interface TeamDao {

    /**
     * Devuelve todos los equipos
     *
     * @return listado de equipos
     */
    List<Team> getAll();

    /**
     * Devuelve el equipo que coincida con el id
     *
     * @param id identificador del equipo
     * @return equipo
     */
    Team getById(int id);

    /**
     * Da de alta un equipo
     *
     * @param team a dar de alta
     * @return identificador del equipo creado
     */
    int persist(Team team);

    /**
     * Elimina un equipo
     *
     * @param teamId identificador del equipo a borrar
     */
    void delete(int teamId);
}
