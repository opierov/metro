package com.solvd.metro.vehicle;

public enum TrainFeature {
    WIFI("Wi-Fi available"),
    AC("Air-conditioned");

    private final String description;

    TrainFeature(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void printFeatureDetails() {
        System.out.println("Feature: " + name() + " - " + description);
    }

    @Override
    public String toString() {
        return name() + " (" + description + ")";
    }
}
