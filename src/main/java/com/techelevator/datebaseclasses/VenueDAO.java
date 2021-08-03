package com.techelevator.datebaseclasses;

import java.util.List;

public interface VenueDAO {
    public List<Venue> getAllVenues();
    public List<Venue> getVenueDetails(long venueID);
}