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
        return jdbcTemplate.query("select * from players order by name ASC",
                new PlayerMapper());

    }

    @Override
    public Player getById(int playerId, int teamId) {
        LOG.trace("Obteniendo jugador con id {} que juega en el equipo con id {}", playerId, teamId);
        try {
            return jdbcTemplate.queryForObject("select * from players where id = ? and team_id = ?",
                    new Object[]{playerId, teamId}, new PlayerMapper());
        } catch (EmptyResultDataAccessException noResult) {
            LOG.info("No existe un jugador con id {} que juegue en el equipo", playerId, teamId);
            return null;
        }
    }

    @Override
    public List<Player> getByTeamId(int teamId) {
        LOG.trace("Obteniendo todos los jugadores del equipo con id " + teamId);
        return jdbcTemplate.query("select * from players where team_id = ?", new Object[]{teamId},
                new PlayerMapper());
    }

    @Override
    public int persist(final Player player) {
        LOG.trace("Creando el jugador {}", player);
        validateTeamId(player.getTeamId());
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
                ps.setInt(5, player.getTeamId());
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

    private void validateTeamId(int teamId) {
        int total = jdbcTemplate.queryForObject("select count(*) from teams where id = ?", new Object[]{teamId}, Integer.class);
        if (total != 1) {
            final String msg = "No existe un equipo con id " + teamId;
            LOG.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }
}
