package com.solvd.metro.vehicle;

import com.solvd.metro.exceptions.CapacityExceededException;
import com.solvd.metro.people.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Autonomous extends Transport implements Boardable, RevenueCalculable {

    private static final Logger logger = LogManager.getLogger(Autonomous.class);
    private final List<Coach> coaches;

    public Autonomous(int id, int number, int coachesCapacity) {
        super(id, number, coachesCapacity);
        this.coaches = new ArrayList<>();
    }

    @Override
    public void addCoach(Passenger passenger) {}

    public List<Coach> getCoaches() {
        return coaches;
    }

    @Override
    public void addCoach(Coach coach) throws CapacityExceededException {
        if (coaches.size() >= coachesCapacity) {
            throw new CapacityExceededException("Autonomous " + number + " has reached maximum coachesCapacity.");
        }
        coaches.add(coach);
        logger.info("Coaches number {} are added to the Autonomous {}.", coach.getNumber(), number);
    }

    @Override
    public BigDecimal calculateRevenue() {
        return BigDecimal.valueOf(coach * 5.0);
    }

    @Override
    public void boardPassenger(Passenger passenger) throws CapacityExceededException {

    }
}