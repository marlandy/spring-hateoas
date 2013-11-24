package com.autentia.tutorial.springhateoas.soccer.dao.jdbc;


import com.autentia.tutorial.springhateoas.soccer.dao.PlayerDao;
import com.autentia.tutorial.springhateoas.soccer.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class PlayerJdbcDao implements PlayerDao {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PlayerJdbcDao(DataSource dataSource) {
        LOG.info("Inicializando PlayerJdbcDao");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Player> getAll() {
        LOG.trace("Obteniendo todos los jugadores");
        return jdbcTemplate.query("select p.*, t.name as team_name from players as p, teams as t where p.team_id = t.id order by p.name ASC",
                new PlayerMapper());

    }

    @Override
    public Player getById(int id) {
        LOG.trace("Obteniendo jugador con id {}", id);
        try {
            return jdbcTemplate.queryForObject("select p.*, t.name as team_name from players as p, teams as t where p.team_id = t.id and p.id = ?",
                    new Object[]{id}, new PlayerMapper());
        } catch (EmptyResultDataAccessException noResult) {
            LOG.info("No existe un jugador con id {}", id);
            return null;
        }
    }

    @Override
    public int persist(final Player player) {
        LOG.trace("Creando el jugador {}", player);
        final String sql = "insert into players (name, age, country, goals, team_id) values (?, ?, ?, ?, ?)";
        final KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, player.getName());
                ps.setInt(2, player.getAge());
                ps.setString(3, player.getCountry());
                ps.setInt(4, player.getGoals());
                ps.setInt(5, getCurrentTeamIdByTeamName(player.getCurrentTeam().getName()));
                return ps;
            }
        }, holder);

        int playerId = holder.getKey().intValue();
        LOG.trace("Jugador creado correctamente con id {}", playerId);
        return playerId;
    }

    @Override
    public void delete(int playerId) {
        LOG.trace("Eliminando jugador con id {}", playerId);
        jdbcTemplate.update("delete from players where id = ?", playerId);
    }

    private int getCurrentTeamIdByTeamName(String teamName) {
        LOG.trace("Obteniendo id del equipo con nombre {}", teamName);
        try {
            return jdbcTemplate.queryForObject("select id from teams where name = ?",
                    new Object[]{teamName}, Integer.class);
        } catch (EmptyResultDataAccessException noResultException) {
            final String msg = "No existe un equipo con nombre " + teamName;
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }
}
