package com.solvd.metro.database;

public class Connection {
    private final int id;

    public Connection(int id) {
        this.id = id;
    }

    public String getName() {
        return "Connection-" + id;
    }

    public String fetchData() {
        return "Mock Data from " + getName();
    }
}
