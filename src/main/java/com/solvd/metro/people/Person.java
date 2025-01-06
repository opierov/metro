package com.solvd.metro.people;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Person implements Maintained {

    private static final Logger logger = LogManager.getLogger(Person.class);

    protected int id;

    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void performTicketInspection() {
        logger.info("Conducting ticket inspection");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return getId() == person.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}