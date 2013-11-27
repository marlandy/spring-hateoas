package com.autentia.tutorial.springhateoas.soccer.dao.jdbc;

import com.autentia.tutorial.springhateoas.soccer.model.Stadium;
import com.autentia.tutorial.springhateoas.soccer.model.TeamShortInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public class StadiumMapper implements RowMapper<Stadium> {

    private static final Logger LOG = LoggerFactory.getLogger(StadiumMapper.class);

    private final JdbcTemplate jdbcTemplate;

    public StadiumMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Stadium mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Stadium stadium = new Stadium();
        stadium.setId(rs.getInt("id"));
        stadium.setCapacity(rs.getInt("capacity"));
        stadium.setCity(rs.getString("city"));
        stadium.setName(rs.getString("name"));
        final Map<String, Object> idAndTeamFromStadium = getTeamNameFromStadiumId(stadium.getId());
        if (idAndTeamFromStadium != null) {
            stadium.setTeam(new TeamShortInfo((Integer)idAndTeamFromStadium.get("id"),
                    (String) idAndTeamFromStadium.get("name")));
        }
        return stadium;
    }

    private Map<String, Object> getTeamNameFromStadiumId(int stadiumId) {
        LOG.trace("Obteniendo el nombre del equipo que juega en el estadio con id {}", stadiumId);
        try {
            return jdbcTemplate.queryForMap("select id, name from teams where stadium_id = ?",
                    new Object[]{stadiumId});
        } catch (EmptyResultDataAccessException noResultException) {
            return null;
        }
    }

}
