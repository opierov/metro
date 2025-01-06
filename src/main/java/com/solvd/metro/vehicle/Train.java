package com.solvd.metro.vehicle;

import com.solvd.metro.exceptions.CapacityExceededException;
import com.solvd.metro.exceptions.InvalidTicketException;
import com.solvd.metro.exceptions.MissingDriverException;
import com.solvd.metro.people.Driver;
import com.solvd.metro.people.Passenger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Train extends Transport implements RevenueCalculable, Driveable {

    private static final Logger logger = LogManager.getLogger(Train.class);
    private final List<Coach> coaches;
    private static int trainCount;
    private Set<TrainFeature> features;

    static {
        trainCount = 0;
    }

    public static int getTrainCount() {
        return trainCount;
    }

    public static void setTrainCount(int trainCount) {
        Train.trainCount = trainCount;
    }

    public Train() {
        super();
        this.coaches = new ArrayList<>();
        trainCount++;
    }

    public Train(int id, int number, int coachesCapacity, Set<TrainFeature> features) {
        super(id, number, coachesCapacity);
        this.coaches = new ArrayList<>();
        this.features = features;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Set<TrainFeature> getFeatures() {
        return features;
    }

    @Override
    public void addCoach(Passenger passenger) {}

    public void addFeature(TrainFeature feature) {
        features.add(feature);
    }

    public void removeFeature(TrainFeature feature) {
        features.remove(feature);
    }

    @Override
    public BigDecimal calculateRevenue() {
        BigDecimal trainRevenue = BigDecimal.ZERO;
        for (Coach coach : coaches) {
            trainRevenue = trainRevenue.add(coach.calculateCarRevenue());
        }
        return trainRevenue;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", coaches=" + coaches +
                ", driver=" + driver +
                '}';
    }

    @Override
    public void addCoach(Coach coach) throws CapacityExceededException {
        if (coaches.size() >= coachesCapacity) {
            throw new CapacityExceededException("Train " + number + " has reached maximum coachesCapacity.");
        }
        coaches.add(coach);
        logger.info("Coaches number {} are added to the Train {}.", coach.getNumber(), number);
    }

    public void checkInDriver(Train train) {
        if (driver == null) {
            logger.error("No driver is assigned to train {}.", number);
            throw new MissingDriverException("No driver is assigned to train " + number + ".");
        }
        logger.info("Driver {} is ready for train {}.", driver.getName(), number);
    }

    public void boardPassengerToTransport(Boardable boardable) throws CapacityExceededException {
        Passenger passenger = new Passenger(3, null);
        boardable.boardPassenger(passenger);
    }

    @Override
    public void startDriving() {
        logger.info("Train {} has started driving!", number);
    }

    public void validateTicket(Passenger passenger) throws InvalidTicketException {
        if (passenger.getTicket() == null || passenger.getTicket().getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTicketException("Passenger " + passenger.getId() + " has an invalid ticket.");
        }
        logger.info("Ticket for passenger {} is valid.", passenger.getId());
    }

}