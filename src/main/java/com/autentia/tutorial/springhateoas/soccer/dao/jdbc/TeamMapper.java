package com.autentia.tutorial.springhateoas.soccer.dao.jdbc;

import com.autentia.tutorial.springhateoas.soccer.model.PlayerShortInfo;
import com.autentia.tutorial.springhateoas.soccer.model.StadiumShortInfo;
import com.autentia.tutorial.springhateoas.soccer.model.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeamMapper implements RowMapper<Team> {

    private final JdbcTemplate jdbcTemplate;

    public TeamMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Team team = new Team();
        team.setId(rs.getInt("id"));
        team.setFoundationYear(rs.getInt("foundation_year"));
        team.setName(rs.getString("name"));
        team.setRankingPosition(rs.getInt("ranking_position"));
        team.setStadium(new StadiumShortInfo(rs.getInt("stadium_id"), rs.getString("stadium_name")));
        team.setPlayers(getTeamPlayers(team.getId()));
        return team;
    }

    private List<PlayerShortInfo> getTeamPlayers(int teamId) {
        return jdbcTemplate.query("select id, name from players where team_id = ?", new Object[]{teamId},
                new RowMapper<PlayerShortInfo>() {
                    @Override
                    public PlayerShortInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new PlayerShortInfo(rs.getInt("id"), rs.getString("name"));
                    }
                });
    }

}
