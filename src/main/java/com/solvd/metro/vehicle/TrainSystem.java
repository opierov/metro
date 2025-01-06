package com.solvd.metro.vehicle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainSystem implements AutoCloseable {

    private static final Logger logger = LogManager.getLogger(TrainSystem.class);
    private final String name;
    private boolean isOperational;

    public TrainSystem(String name) {
        this.name = name;
        this.isOperational = false;
    }

    public String getName() {
        return name;
    }

    public boolean isOperational() {
        return isOperational;
    }

    public void setOperational(boolean operational) {
        isOperational = operational;
    }

    public void performRepair() {
        logger.info("Repair of trains on the {}", name);
        isOperational = true;
        logger.info("Trains on the {} are now operational.", name);
    }

    @Override
    public void close() {
        if (isOperational) {
            logger.info("Subway system open. Trains on the {} are ready for service.", name);
        } else {
            logger.error("Subway system closed. Trains on the {} requires further repairs.", name);
        }
    }

}