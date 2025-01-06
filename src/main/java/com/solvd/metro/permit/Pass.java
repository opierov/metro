package com.solvd.metro.permit;

import com.solvd.metro.infrastructure.Station;

public class Pass {

    private boolean access;
    private Station station;

    public Pass(boolean access, Station station) {
        this.access = access;
        this.station = station;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public boolean getId() {
        return access;
    }

    public void setId(String id) {
        this.access = access;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

}