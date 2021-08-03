package com.techelevator.datebaseclasses.jdbc;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.techelevator.datebaseclasses.Space;
import com.techelevator.datebaseclasses.SpaceDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSpaceDAO implements SpaceDAO {
    private JdbcTemplate jdbcTemplate;

    public JDBCSpaceDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Space> getAllSpaces() {
        String sql = "SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy FROM space";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
        List<Space> spaces = new ArrayList<Space>();

        while(row.next()) {
            spaces.add(mapRowToSpace(row));
        }

        return spaces;
    }

    @Override
    public List<Space> getSpacesById(long id) {
        String sql = "SELECT id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy FROM space WHERE id = ?";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, id);
        List<Space> spaces = new ArrayList<Space>();

        while(row.next()) {
            spaces.add(mapRowToSpace(row));
        }

        return spaces;
    }

    private Space mapRowToSpace(SqlRowSet result) {
        Space spaces = new Space();
        spaces.setId(result.getLong("id"));
        spaces.setVenue_id(result.getLong("venue_id"));
        spaces.setName(result.getString("name"));
        spaces.setIs_accessible(result.getBoolean("is_accessible"));
        spaces.setOpen_from(result.getString("open_from_mm"));
        spaces.setOpen_to(result.getString("open_to_mm"));
        spaces.setDaily_rate(result.getDouble("daily_rate"));
        spaces.setMax_occupancy(result.getLong("max_occupancy"));

        return spaces;
    }

}