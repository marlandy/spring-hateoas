package com.autentia.tutorial.springhateoas.soccer.dao.jdbc;

import com.autentia.tutorial.springhateoas.soccer.model.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamMapper implements RowMapper<Team> {

    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Team team = new Team();
        team.setTeamId(rs.getInt("id"));
        team.setFoundationYear(rs.getInt("foundation_year"));
        team.setName(rs.getString("name"));
        team.setRankingPosition(rs.getInt("ranking_position"));
        return team;
    }

}
