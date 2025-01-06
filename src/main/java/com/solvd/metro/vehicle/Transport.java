package com.solvd.metro.vehicle;

import com.solvd.metro.exceptions.CapacityExceededException;
import com.solvd.metro.people.Driver;
import com.solvd.metro.people.Passenger;

public abstract class Transport  {

    protected int id;
    public int number;
    protected int coachesCapacity;
    protected int coach;
    protected Driver driver;

    public Transport() {

    }

    public Transport(int id, int number, int coachesCapacity) {
         this.id = id;
         this.number = number;
         this.coachesCapacity = coachesCapacity;
         this.coach = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoachesCapacity() {
        return coachesCapacity;
    }

    public void setCoachesCapacity(int coachesCapacity) {
        this.coachesCapacity = coachesCapacity;
    }

    public int getCoach() {
        return coach;
    }

    public void setCoach(int coach) {
        this.coach = coach;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setNumber(int number) {
         this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public abstract void addCoach(Passenger passenger) throws CapacityExceededException;

    public abstract void addCoach(Coach coach) throws CapacityExceededException;

}