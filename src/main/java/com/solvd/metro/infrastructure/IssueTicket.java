package com.solvd.metro.infrastructure;

import com.solvd.metro.people.Passenger;
import com.solvd.metro.permit.Ticket;

public interface IssueTicket {

    Ticket issueTicket(Passenger passenger);

}