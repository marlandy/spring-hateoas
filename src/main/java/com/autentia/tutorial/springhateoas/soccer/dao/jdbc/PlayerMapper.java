package com.autentia.tutorial.springhateoas.soccer.dao.jdbc;

import com.autentia.tutorial.springhateoas.soccer.model.Player;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Player player = new Player();
        player.setAge(rs.getInt("age"));
        player.setCountry(rs.getString("country"));
        player.setPlayerId(rs.getInt("id"));
        player.setName(rs.getString("name"));
        player.setGoals(rs.getInt("goals"));
        player.setTeamId(rs.getInt("team_id"));
        return player;
    }

}
