package com.techelevator.datebaseclasses;

public class Space {

    private long id;
    private long venue_id;
    private String name;
    private boolean is_accessible;
    private String open_from;
    private String open_to;
    private double daily_rate;
    private long max_occupancy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(long venue_id) {
        this.venue_id = venue_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_accessible() {
        return is_accessible;
    }

    public void setIs_accessible(boolean is_accessible) {
        this.is_accessible = is_accessible;
    }

    public String getOpen_from() {
        return open_from;
    }

    public void setOpen_from(String open_from) {
        this.open_from = open_from;
    }

    public String getOpen_to() {
        return open_to;
    }

    public void setOpen_to(String open_to) {
        this.open_to = open_to;
    }

    public double getDaily_rate() {
        return daily_rate;
    }

    public void setDaily_rate(double daily_rate) {
        this.daily_rate = daily_rate;
    }

    public long getMax_occupancy() {
        return max_occupancy;
    }

    public void setMax_occupancy(long max_occupancy) {
        this.max_occupancy = max_occupancy;
    }

}