package com.autentia.tutorial.springhateoas.soccer.dao.jdbc;

import com.autentia.tutorial.springhateoas.soccer.model.Player;
import com.autentia.tutorial.springhateoas.soccer.model.TeamShortInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerMapper.class);

    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Player player = new Player();
        player.setAge(rs.getInt("age"));
        player.setCountry(rs.getString("country"));
        player.setId(rs.getInt("id"));
        player.setName(rs.getString("name"));
        player.setGoals(rs.getInt("goals"));
        player.setCurrentTeam(new TeamShortInfo(rs.getInt("team_id"), rs.getString("team_name")));
        return player;
    }

}
