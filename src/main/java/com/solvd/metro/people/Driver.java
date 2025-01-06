package com.solvd.metro.people;

import java.time.LocalDate;

public class Driver extends Person {

    private String name;
    private LocalDate hireDate;
    private Double hourlyRate;

    public Driver(int id, String name, LocalDate hireDate, Double hourlyRate) {
        super(id);
        this.name = name;
        this.hireDate = hireDate;
        this.hourlyRate = hourlyRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

}