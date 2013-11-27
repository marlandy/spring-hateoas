package com.autentia.tutorial.springhateoas.soccer.dao.jdbc;

import com.autentia.tutorial.springhateoas.soccer.dao.TeamDao;
import com.autentia.tutorial.springhateoas.soccer.model.Team;
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
public class TeamJdbcDao implements TeamDao {

    private static final Logger LOG = LoggerFactory.getLogger(StadiumJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeamJdbcDao(DataSource datasource) {
        LOG.info("Inicializando TeamJdbcDao");
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<Team> getAll() {
        LOG.trace("Obteniendo todos los equipos");
        return jdbcTemplate.query("select * from teams order by ranking_position ASC",
                new TeamMapper());
    }

    @Override
    public Team getById(int id) {
        LOG.trace("Obteniendo el equipo con id {}", id);
        try {
            return jdbcTemplate.queryForObject("select * from teams where id = ?",
                    new Object[]{id}, new TeamMapper());
        } catch (EmptyResultDataAccessException noResult) {
            LOG.info("No existe un equipo con id {}", id);
            return null;
        }
    }

    @Override
    public int persist(final Team team) {
        LOG.trace("Creando el equipo {}", team);

        final String sql = "insert into teams (name, foundation_year, ranking_position) values (?, ?, ?)";
        final KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, team.getName());
                ps.setInt(2, team.getFoundationYear());
                ps.setInt(3, team.getRankingPosition());
                return ps;
            }
        }, holder);

        int teamId = holder.getKey().intValue();
        LOG.trace("Equipo creado correctamente con id {}", teamId);
        return teamId;
    }

    @Override
    public void delete(int teamId) {
        LOG.trace("Eliminando equipo con id {}", teamId);
        jdbcTemplate.update("delete from teams where id = ?", teamId);
    }

}
