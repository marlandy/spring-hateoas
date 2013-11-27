package com.autentia.tutorial.springhateoas.soccer.dao.jdbc;

import com.autentia.tutorial.springhateoas.soccer.model.Stadium;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class StadiumMapper implements RowMapper<Stadium> {


    @Override
    public Stadium mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Stadium stadium = new Stadium();
        stadium.setStadiumId(rs.getInt("id"));
        stadium.setCapacity(rs.getInt("capacity"));
        stadium.setCity(rs.getString("city"));
        stadium.setName(rs.getString("name"));
        stadium.setTeamId(rs.getInt("team_id"));
        return stadium;
    }


}
