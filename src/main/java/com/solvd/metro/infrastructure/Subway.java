package com.solvd.metro.infrastructure;

import com.solvd.metro.exceptions.CapacityExceededException;
import com.solvd.metro.people.Passenger;
import com.solvd.metro.permit.Ticket;
import com.solvd.metro.vehicle.Transport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class Subway implements IssueTicket {

    private static final Logger logger = LogManager.getLogger(Subway.class);

    private String name;
    private final Map<String, Line> lines;

    public Subway(String name, int id) {
        this.name = name;
        this.lines = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Line> getLines() {
        return lines;
    }

    public void addLine(Line line) {
        lines.put(line.getLineName().name(), line);
    }

    public BigDecimal calculateTotalRevenue() {
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Line line : lines.values()) {
            totalRevenue = totalRevenue.add(line.calculateLineRevenue());
        }
        return totalRevenue;
    }

    public void checkInPassenger(Transport transport, Passenger passenger) {
        logger.info("Checking in passenger {} in Transport {}", passenger.getId(), transport.getNumber());
        try {
            transport.addCoach(passenger);
        } catch (CapacityExceededException e) {
            logger.error("Error checking in passenger {}: {}", passenger.getId(), e.getMessage());
        }
    }

    @Override
    public Ticket issueTicket(Passenger passenger) {
        Ticket ticket = passenger.getTicket();
        logger.info("Ticket issued: {}", ticket);
        return ticket;
    }

}