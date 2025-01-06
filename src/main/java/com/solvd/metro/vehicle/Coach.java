package com.solvd.metro.vehicle;

import com.solvd.metro.exceptions.CoachFullException;
import com.solvd.metro.people.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Coach {

    private static final Logger logger = LogManager.getLogger(Coach.class);

    private final int number;
    private Set<Passenger> passengers;
    private int passengersCapacity;

    public Coach(int number, int passengersCapacity) {
        this.number = number;
        this.passengersCapacity = passengersCapacity;
        passengers = new HashSet<>();
    }

    public int getNumber() {
        return number;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    public int getPassengersCapacity() {
        return passengersCapacity;
    }

    public void setPassengersCapacity(int passengersCapacity) {
        this.passengersCapacity = passengersCapacity;
    }

    public void addPassenger(Passenger passenger) {
        if (passengers.size() < passengersCapacity) {
            passengers.add(passenger);
            logger.info("Passenger {} added to coach {}", passenger.getId(), number);
        } else {
            throw new CoachFullException("Cannot add more passengers to this coach. It is full.");
        }
    }

    public BigDecimal calculateCarRevenue() {
        BigDecimal carRevenue = BigDecimal.ZERO;
        for (Passenger passenger : passengers) {
            carRevenue = carRevenue.add(passenger.getTicket().getPrice());
        }
        return carRevenue;
    }

}