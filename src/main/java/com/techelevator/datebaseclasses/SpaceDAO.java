package com.techelevator.datebaseclasses;

import java.util.List;

public interface SpaceDAO {
    public List<Space> getAllSpaces();
    public List<Space> getSpacesById(long id);
}