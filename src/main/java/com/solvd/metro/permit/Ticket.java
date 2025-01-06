package com.solvd.metro.permit;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Ticket {

    private Integer id;
    private LocalDateTime issuedAt;
    private BigDecimal price;
    private Pass pass;

    public Ticket() {
    }

    public Ticket(Integer id, LocalDateTime issuedAt, BigDecimal price, Pass pass) {
        this.id = id;
        this.issuedAt = issuedAt;
        this.price = price;
        this.pass = pass;
    }

    public Ticket(TicketType ticketType) {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }

    public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getTicketId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(getId(), ticket.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIssuedAt(), getPrice(), pass);
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", issuedAt=" + (issuedAt != null ? issuedAt : "No Ticket Issued") +
                ", price=" + (price != null ? price : "No Price") +
                ", pass=" + (pass != null ? pass : "No Pass") +
                '}';
    }

    public TicketType getTicketType() {
        return null;
    }

}