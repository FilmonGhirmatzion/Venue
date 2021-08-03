package com.techelevator.datebaseclasses;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO
{
    public List<Reservation> getAllReservations();
    public Reservation getReservationById(long reservationId);
    public List<Space> getSpacesByTheStartAndEndDate(long venueId, LocalDate fromDate, LocalDate toDate);
    public Reservation createReservation(Reservation newReservation);
    public void saveReservation(Reservation savedReservation);

}
