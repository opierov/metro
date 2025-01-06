package com.solvd.metro.permit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum TicketType {
    STANDARD("Standard Ticket", 5.00),
    TRAVEL_CARD("Discounted Ticket", 3.50);

    private static final Logger logger = LogManager.getLogger(TicketType.class);

    private final String description;
    private final double basePrice;

    TicketType(String description, double basePrice) {
        this.description = description;
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double calculateFinalPrice(double multiplier) {
        return basePrice * multiplier;
    }

    public void printTicketSummary() {
        logger.info("Ticket Summary:");
        logger.info("Type: {}", this.name());
        logger.info("Description: {}", description);
        logger.info("Base Price: ${}", basePrice);
    }

    @Override
    public String toString() {
        return description + " - $" + basePrice;
    }
}
