package com.techelevator.datebaseclasses.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.techelevator.datebaseclasses.Reservation;
import com.techelevator.datebaseclasses.ReservationDAO;
import com.techelevator.datebaseclasses.Space;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationDAO implements ReservationDAO {
    private JdbcTemplate jdbcTemplate;

    public JDBCReservationDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Reservation> getAllReservations() {
        String sql = "SELECT reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for FROM reservation";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql);
        List<Reservation> reservations = new ArrayList<Reservation>();

        while(row.next()) {
            reservations.add(mapRowToReservation(row));
        }

        return reservations;
    }

    @Override
    public List<Space> getSpacesByTheStartAndEndDate(long venueId, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT space.id, space.name, daily_rate, max_occupancy, is_accessible, daily_rate FROM space JOIN venue ON venue.id = space.venue_id WHERE space.venue_id = ? AND space.id NOT IN (SELECT space_id FROM reservation WHERE (?, ?) OVERLAPS (start_date, end_date) GROUP BY space_id) LIMIT 5";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, venueId, startDate, endDate.plusDays(1));
        List<Space> spaces = new ArrayList<Space>();

        while(row.next()) {
            spaces.add(mapRowToSpacesForReservationUse(row));
        }

        return spaces;
    }

    @Override
    public Reservation getReservationById(long reservationId) {
        String sql = "SELECT reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for FROM reservation WHERE reservation_id = ?";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, reservationId);
        Reservation reservation = null;

        while (row.next()) {
            reservation = mapRowToReservation(row);
        }

        return reservation;
    }

    @Override
    public Reservation createReservation(Reservation newReservation) {
        String sql = "INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, newReservation.getSpace_id(), newReservation.getNumber_of_attendees(), newReservation.getStart_date(), newReservation.getEnd_date(), newReservation.getReserved_for());

        return newReservation;
    }

    @Override
    public void saveReservation(Reservation updatedReservation) {
        String sql = "UPDATE reservation SET reserved_for = ? WHERE reservation_id = ?";
        jdbcTemplate.update(sql, updatedReservation.getReserved_for(), updatedReservation.getReservation_id());
    }


    private Space mapRowToSpacesForReservationUse(SqlRowSet result) {
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

    private Reservation mapRowToReservation(SqlRowSet result) {
        Reservation reservations = new Reservation();
        reservations.setReservation_id(result.getLong("reservation_id"));
        reservations.setSpace_id(result.getLong("space_id"));
        reservations.setNumber_of_attendees(result.getLong("number_of_attendees"));
        reservations.setStart_date(result.getDate("start_date").toLocalDate());
        reservations.setEnd_date(result.getDate("end_date").toLocalDate());
        reservations.setReserved_for(result.getString("reserved_for"));

        return reservations;
    }
}
