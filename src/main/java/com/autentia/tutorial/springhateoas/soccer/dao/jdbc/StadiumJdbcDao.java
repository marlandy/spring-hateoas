package com.autentia.tutorial.springhateoas.soccer.dao.jdbc;

import com.autentia.tutorial.springhateoas.soccer.dao.StadiumDao;
import com.autentia.tutorial.springhateoas.soccer.model.Stadium;
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
public class StadiumJdbcDao implements StadiumDao {

    private static final Logger LOG = LoggerFactory.getLogger(StadiumJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StadiumJdbcDao(DataSource datasource) {
        LOG.info("Inicializando StadiumJdbcDAO");
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<Stadium> getAll() {
        LOG.trace("Obteniendo todos los estadios");
        return jdbcTemplate.query("select * from stadiums", new StadiumMapper());
    }

    @Override
    public Stadium getById(int id) {
        LOG.trace("Obteniendo estadio con id {}", id);
        try {
            return jdbcTemplate.queryForObject("select * from stadiums where id = ?", new Object[]{id},
                    new StadiumMapper());
        } catch (EmptyResultDataAccessException noResult) {
            LOG.info("No existe un estadio con id {}", id);
            return null;
        }
    }

    @Override
    public Stadium getByTeamId(int teamId) {
        LOG.trace("Obteniendo estadio del equipo con id {}", teamId);
        try {
            return jdbcTemplate.queryForObject("select * from stadiums where team_id = ?", new Object[]{teamId},
                    new StadiumMapper());
        } catch (EmptyResultDataAccessException noResult) {
            LOG.info("No existe el estadio del equipo con id {}", teamId);
            return null;
        }
    }

    @Override
    public int persist(final Stadium stadium) {

        LOG.trace("Creando el estadio {}", stadium);

        validateTeamId(stadium.getTeamId());
        final String sql = "insert into stadiums (name, capacity, city, team_id) values (?, ?, ?, ?)";
        final KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, stadium.getName());
                ps.setInt(2, stadium.getCapacity());
                ps.setString(3, stadium.getCity());
                ps.setInt(4, stadium.getTeamId());
                return ps;
            }
        }, holder);

        int newStadiumId = holder.getKey().intValue();
        LOG.trace("Estadio creado correctamente con id {}", newStadiumId);
        return newStadiumId;
    }

    @Override
    public void delete(int stadiumId) {
        LOG.trace("Eliminando estadio con id {}", stadiumId);
        jdbcTemplate.update("delete from stadiums where id = ?", stadiumId);
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
