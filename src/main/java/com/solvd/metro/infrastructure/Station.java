package com.solvd.metro.infrastructure;

public class Station {

    private StationName stationName;

    public Station() {
        this.stationName = stationName;
    }

    public StationName getStationName() {
        return stationName;
    }

    public void setStationName(StationName stationName) {
        this.stationName = stationName;
    }

    @Override
    public String toString() {
        return "Station{" +
                "stationName=" + stationName +
                '}';
    }
}