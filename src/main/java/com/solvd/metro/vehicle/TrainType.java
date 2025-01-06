package com.solvd.metro.vehicle;

import com.solvd.metro.infrastructure.StationName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public enum TrainType {
    REGULAR(Set.of(StationName.CENTRAL, StationName.NORTH, StationName.SOUTH, StationName.EAST, StationName.WEST)),
    EXPRESS(Set.of(StationName.CENTRAL, StationName.NORTH, StationName.SOUTH));

    private static final Logger logger = LogManager.getLogger(TrainType.class);

    private final Set<StationName> operatingStations;

    TrainType(Set<StationName> operatingStations) {
        this.operatingStations = operatingStations;
    }

    public Set<StationName> getOperatingStations() {
        return operatingStations;
    }

    public int getNumberOfOperatingStations() {
        return operatingStations.size();
    }

    public void logStationCount() {
        logger.info("{} Train operates at {} stations.", this.name(), getNumberOfOperatingStations());
    }
}
