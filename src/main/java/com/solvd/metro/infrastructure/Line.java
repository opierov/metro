package com.solvd.metro.infrastructure;

import com.solvd.metro.vehicle.Train;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Line {

    private int id;
    private LineName lineName;
    private final List<Train> trains;

    public Line(LineName lineName, int id) {
        this.lineName = lineName;
        this.trains = new ArrayList<>();
    }

    public LineName getLineName() {
        return lineName;
    }

    public void setLineName(LineName lineName) {
        this.lineName = lineName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void addTrain(Train train) {
        trains.add(train);
    }

    public BigDecimal calculateLineRevenue() {
        BigDecimal lineRevenue = BigDecimal.ZERO;
        for (Train train : trains) {
            if (train != null) {
                lineRevenue = lineRevenue.add(train.calculateRevenue());
            }
        }
        return lineRevenue;
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", lineName=" + lineName +
                ", trains=" + trains +
                '}';
    }
}