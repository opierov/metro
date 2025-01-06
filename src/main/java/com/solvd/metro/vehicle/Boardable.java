package com.solvd.metro.vehicle;

import com.solvd.metro.exceptions.CapacityExceededException;
import com.solvd.metro.people.Passenger;

public interface Boardable {

    void boardPassenger(Passenger passenger) throws CapacityExceededException;

}