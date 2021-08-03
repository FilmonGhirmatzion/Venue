package com.techelevator.datebaseclasses.jdbc;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.techelevator.datebaseclasses.Venue;
import com.techelevator.datebaseclasses.VenueDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCVenueDAO implements VenueDAO {
    private JdbcTemplate jdbcTemplate;

    public JDBCVenueDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Venue> getAllVenues() {
        List<Venue> venues = new ArrayList<Venue>();
        String sql = "SELECT id, name, city_id, description FROM venue ORDER BY name";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql);

        while(row.next()) {
            venues.add(mapRowToVenue(row));
        }

        return venues;
    }

    @Override
    public List<Venue> getVenueDetails(long venueId) {
        String sql = "SELECT venue.name, city.name AS city, city.state_abbreviation AS state, category.name AS category, description FROM venue JOIN city ON city.id = venue.city_id JOIN category_venue ON category_venue.venue_id = venue.id JOIN category ON category.id = category_venue.category_id WHERE venue.id = ?;";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, venueId);
        List<Venue> venue = new ArrayList<Venue>();

        while (rows.next()){
            Venue venue1 = mapRowToVenue(rows);
            venue.add(venue1);
        }

        return venue;
    }

    private Venue mapRowToVenue(SqlRowSet result) {
        Venue venue = new Venue();
        venue.setId(result.getLong("id"));
        venue.setName(result.getString("name"));
        venue.setCity_id(result.getLong("city_id"));
        venue.setDescription(result.getString("description"));
        return venue;
    }


}