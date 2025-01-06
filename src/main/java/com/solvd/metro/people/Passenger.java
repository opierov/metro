package com.solvd.metro.people;

import com.solvd.metro.permit.Ticket;

import java.util.Objects;

public class Passenger extends Person {

    private Ticket ticket;

    public Passenger(int id, Ticket ticket) {
        super(id);
        this.ticket = ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger passenger)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getTicket(), passenger.getTicket());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTicket());
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", ticket=" + ticket +
                '}';
    }
}