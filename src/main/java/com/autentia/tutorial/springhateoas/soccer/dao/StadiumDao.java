package com.autentia.tutorial.springhateoas.soccer.dao;

import com.autentia.tutorial.springhateoas.soccer.model.Stadium;

import java.util.List;

public interface StadiumDao {

    /**
     * Devuelve todos los estadios
     *
     * @return listado de estadios
     */
    List<Stadium> getAll();

    /**
     * Devuelve el estadio que coincida con el id
     *
     * @param id identificador del estadio
     * @return estadio
     */
    Stadium getById(int id);

    /**
     * Devuelve el estadio que cuyo team_id coincida
     *
     * @param teamId identificador del equipo
     * @return estadio
     */
    Stadium getByTeamId(int teamId);

    /**
     * Da de alta un estadio
     *
     * @param stadium a dar de alta
     * @return identificador del estadio creado
     */
    int persist(Stadium stadium);

    /**
     * Borra un estadio
     *
     * @param stadiumId id del estadio a borrar
     */
    void delete(int stadiumId);

}
