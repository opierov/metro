package com.solvd.metro;

import com.solvd.metro.database.Connection;
import com.solvd.metro.database.ConnectionPool;
import com.solvd.metro.exceptions.CapacityExceededException;
import com.solvd.metro.exceptions.CoachFullException;
import com.solvd.metro.exceptions.InvalidTicketException;
import com.solvd.metro.functional.Processor;
import com.solvd.metro.infrastructure.*;
import com.solvd.metro.vehicle.*;
import com.solvd.metro.permit.TicketType;

import com.solvd.metro.people.Driver;
import com.solvd.metro.people.Passenger;
import com.solvd.metro.permit.Pass;
import com.solvd.metro.permit.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Field[] passField;

    public static void main(String[] args) throws CapacityExceededException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Station station = new Station();
        station.setStationName(StationName.CENTRAL);

        Ticket ticket1 = new Ticket(101, LocalDateTime.now(), new BigDecimal("6.50"), new Pass(true, station));

        Ticket ticket2 = new Ticket();
        ticket2.setId(102);
        ticket2.setIssuedAt(LocalDateTime.now());
        ticket2.setPrice(new BigDecimal("3.00"));

        Ticket ticket4 = new Ticket(108, LocalDateTime.now(), new BigDecimal("9.50"), null);

        Passenger passenger1 = new Passenger(1, ticket1);
        Passenger passenger2 = new Passenger(2, ticket2);
        Passenger passenger3 = new Passenger(3, ticket4);


        Coach coach1 = new Coach(45, 1);
        coach1.addPassenger(passenger1);
        Coach coach2 = new Coach(46, 1);
        coach2.addPassenger(passenger2);

        Driver driver = new Driver(0, "Max", LocalDate.of(2015, 4, 12), 20.0);

        Train train1 = new Train(1, 7, 2, Set.of(TrainFeature.AC));
        train1.addCoach(coach1);
        train1.setDriver(driver);


        Transport autonomousTrain = new Autonomous(2, 8, 2); // Autonomous train does not need a driver

        autonomousTrain.addCoach(coach1);
        autonomousTrain.addCoach(coach2);

        //Line redLine = new Line("Red line", 1);
        Line redLine = new Line(LineName.RED_LINE, 1);
        redLine.addTrain(train1);

        Subway subwaySystem = new Subway("Warsaw Metro", 1);
        subwaySystem.addLine(redLine);

        Ticket ticket3 = subwaySystem.issueTicket(passenger1);

        RevenueCalculable revenueCalculableTrain = train1;
        logger.info("Train Revenue: ${}", revenueCalculableTrain.calculateRevenue());

        RevenueCalculable revenueCalculableAutonomous = (RevenueCalculable) autonomousTrain;
        logger.info("Autonomous Train Revenue: ${}", revenueCalculableAutonomous.calculateRevenue());

        Driveable driveableTrain = train1;
        driveableTrain.startDriving();

        BigDecimal totalRevenue = subwaySystem.calculateTotalRevenue();
        subwaySystem.checkInPassenger(train1, passenger1);
        logger.info("Total Subway System Revenue: ${}", totalRevenue);

        passenger1.performTicketInspection();
        //new Subway("Tokyo", 1).checkInPassenger(autonomousTrain, passenger2);
        train1.checkInDriver(train1);

        //method which accepts a parameter of the Interface type, class field with such type
        train1.boardPassengerToTransport((Boardable) autonomousTrain);

        System.out.println("My homework 5. Examples of using exceptions");
        try {
            Train train5 = new Train(1, 1, 1, Set.of(TrainFeature.WIFI));
            Passenger passenger5 = new Passenger(1, null); // Invalid ticket

            train5.validateTicket(passenger5); // This will throw exception

        } catch (InvalidTicketException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            coach1.addPassenger(passenger1);
            coach1.addPassenger(passenger2); // This will throw exception
        } catch (CoachFullException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Finalizing the boarding process...");
        }

        try (TrainSystem newLine = new TrainSystem("Blue Line")) {

            newLine.performRepair();

        } catch (Exception e) {
            System.out.println("An error occurred during repair: " + e.getMessage());
        }

        System.out.println("My homework 9. Examples of using enums");

        TrainType firstTrain = TrainType.EXPRESS;
        logger.info("Express Train Operates on Stations: {}", firstTrain.getOperatingStations());

        TrainType secondTrain = TrainType.REGULAR;
        logger.info("Express Train Operates on Stations: {}", secondTrain.getOperatingStations());

        StationName station2 = StationName.NORTH;

        if (TrainType.REGULAR.getOperatingStations().contains(station2)) {
            logger.info("Regular train operates at {}", station2);
        } else {
            logger.info("Regular train does not operate at {}", station2);
        }

        if (firstTrain.getNumberOfOperatingStations() > secondTrain.getNumberOfOperatingStations()) {
            logger.info("EXPRESS Train covers more stations than REGULAR Train.");
        } else {
            logger.info("REGULAR Train covers more stations than EXPRESS Train.");
        }

        TicketType.STANDARD.printTicketSummary();
        TicketType.TRAVEL_CARD.printTicketSummary();

        double weekendMultiplier = 0.5;
        double rushHourMultiplier = 1.5;

        double standardWeekendPrice = TicketType.STANDARD.calculateFinalPrice(weekendMultiplier);
        double standardRushHourPrice = TicketType.STANDARD.calculateFinalPrice(rushHourMultiplier);
        logger.info("Final price for STANDARD ticket on weekends: ${}", String.format("%.2f", standardWeekendPrice));
        logger.info("Final price for STANDARD ticket on weekends: ${}", String.format("%.2f", standardRushHourPrice));

        System.out.println("My homework 9. Examples of using lambda");

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger(10, new Ticket(101, LocalDateTime.now(), new BigDecimal("5.50"), null)));
        passengers.add(new Passenger(11, null));
        passengers.add(new Passenger(12, new Ticket(102, LocalDateTime.now(), new BigDecimal("8.00"), null)));

        Predicate<Passenger> hasNoTicket = passenger -> passenger.getTicket() == null;

        List<Passenger> passengersWithoutTickets = new ArrayList<>();
        for (Passenger passenger : passengers) {
            if (hasNoTicket.test(passenger)) {
                passengersWithoutTickets.add(passenger);
            }
        }

        for (Passenger p : passengersWithoutTickets) {
            logger.info("Passengers without tickets. Passenger ID: {}", p.getId());
        }

        Consumer<Passenger> printPassenger = passenger -> {
            if (passenger.getTicket() != null) {
                logger.info("Passenger {} has a ticket.", passenger.getId());
            } else {
                logger.info("Passenger {} does not have a ticket.", passenger.getId());
            }
        };

        passengers.forEach(printPassenger);

        Supplier<Passenger> defaultPassengerSupplier = () -> new Passenger(0, null);
        Passenger defaultPassenger = defaultPassengerSupplier.get();
        logger.info("Default Passenger: ID = {}", defaultPassenger.getId());

        Function<BigDecimal, BigDecimal> applyDiscount = price -> price.multiply(new BigDecimal("0.90"));
        Ticket ticket = passengers.get(0).getTicket();
        if (ticket != null) {
            BigDecimal discountedPrice = applyDiscount.apply(ticket.getPrice());
            logger.info("Original Price: {}, Discounted Price: {}", ticket.getPrice(), discountedPrice);
        }

        Runnable boardTrain = () -> {
            logger.info("Boarding passengers...");
            for (Passenger p : passengers) {
                if (p.getTicket() != null) {
                    logger.info("Passenger {} boarded successfully.", p.getId());
                } else {
                    logger.info("Passenger {} cannot board without a ticket.", p.getId());
                }
            }
            logger.info("Boarding process completed.");
        };

        boardTrain.run();

        System.out.println("My homework 9. Examples of using custom lambda");

        Processor<Passenger> printPassengerId = passenger ->
                logger.info("Processing passenger with ID: {}", passenger.getId());

        for (Passenger passenger : passengers) {
            printPassengerId.process(passenger);
        }

        System.out.println("My homework 10. Examples of using 10 collection streaming operations");

        List<StationName> stationNames = Arrays.asList(StationName.values());

        //Filter
        List<StationName> stationsStartingWithC = stationNames.stream()
                .filter(name -> name.name().startsWith("C"))
                .toList();
        stationsStartingWithC.forEach(name -> logger.info("Station: {}", name));

        //Map
        List<Integer> ticketIds = passengers.stream()
                .filter(p -> p.getTicket() != null)
                .map(p -> p.getTicket().getTicketId())
                .toList();
        logger.info("Ticket IDs of passengers with tickets: {}", ticketIds);

        List<StationName> lines1 = List.of(StationName.CENTRAL, StationName.NORTH, StationName.SOUTH);
        List<StationName> lines2 = List.of(StationName.EAST, StationName.WEST, StationName.NORTH);

        //FlatMap
        List<StationName> allStations = Stream.of(lines1, lines2)
                .flatMap(Collection::stream)
                .distinct()
                .toList();

        logger.info("All unique stations {}", allStations);

        //Peek
        passengers.stream()
                .peek(p -> logger.info("Processing passenger ID: {}", p.getId()))
                .filter(p -> p.getTicket() != null)
                .forEach(p -> logger.info("Passenger with ticket: {}", p.getId()));

        //ForEach
        passengers.stream()
                .filter(p -> p.getTicket() != null)
                .forEach(p -> logger.info("Passenger {} has ticket price: {}", p.getId(), p.getTicket().getPrice()));

        //Collect
        Map<Boolean, List<Passenger>> groupedByTicketPresence = passengers.stream()
                .collect(Collectors.groupingBy(p -> p.getTicket() != null));
        logger.info("Grouped by ticket presence: {}", groupedByTicketPresence);

        //Count
        long passengersWithoutTicketsCount = passengers.stream()
                .filter(p -> p.getTicket() == null)
                .count();
        logger.info("Number of passengers without tickets: {}", passengersWithoutTicketsCount);

        //AnyMatch
        boolean anyTicketAbove7 = passengers.stream()
                .filter(p -> p.getTicket() != null)
                .anyMatch(p -> p.getTicket().getPrice().compareTo(new BigDecimal("7.00")) > 0);
        logger.info("Any ticket priced above 7.00: " + anyTicketAbove7);

        //AllMatch
        boolean allTicketsValid = passengers.stream()
                .filter(p -> p.getTicket() != null)
                .allMatch(p -> p.getTicket().getPrice().compareTo(BigDecimal.ZERO) > 0);
        logger.info("All tickets have valid prices: {}", allTicketsValid);

        //FindFirst
        Optional<Passenger> firstPassengerWithTicket = passengers.stream()
                .filter(p -> p.getTicket() != null)
                .findFirst();
        firstPassengerWithTicket.ifPresent(p ->
                logger.info("First passenger with ticket: {}", p.getId()));

        Optional<String> optional = Optional.of("Welcome to the metro!");

        optional.ifPresent(logger::info);

        String value = optional.orElse("Welcome to the subway!");
        logger.info(value);

        optional.ifPresent(logger::info);

        Optional<Integer> length = optional.map(String::length);
        length.ifPresent(len -> logger.info("Length: {}", len));

        System.out.println("My homework 10. Examples of using reflection");

        Class<?> passengerClass = Class.forName("com.solvd.metro.people.Passenger");

        for (Field field : passengerClass.getDeclaredFields()) {
            logger.info("Field: {}", field.getName());
        }

        for (Constructor<?> constructor : passengerClass.getDeclaredConstructors()) {
            logger.info("Constructor: {}", constructor.getName());
        }

        for (Method method : passengerClass.getDeclaredMethods()) {
            logger.info("Method: {}", method.getName());
        }

        Constructor<?> constructor = passengerClass.getConstructor(int.class, Ticket.class);

        Ticket obTicket = new Ticket(101, LocalDateTime.now(), new BigDecimal("10.56"), null);

        Object passengerInstance = constructor.newInstance(1, obTicket);

        Method toStringMethod = passengerClass.getMethod("toString");

        Object result = toStringMethod.invoke(passengerInstance);

        logger.info(result);

        System.out.println("My homework 11. Threads using Runnable and Thread");

        Runnable validatingTask = () -> {
            logger.info("Ticket validation process started");
            for (Passenger passenger : passengers) {
                if (passenger.getTicket() == null) {
                    logger.info("Passenger with ID: {} please, validate your train ticket", passenger.getId());
                } else {
                    logger.info("Passenger with ID: {} has a ticket", passenger.getId());
                }
            }
            logger.info("Ticket validation process finished");
        };

        Runnable checkingTask = () -> {
            logger.info("Checking ticket process started");
            for (Passenger passenger : passengers) {
                if (passenger.getTicket() != null) {
                    logger.info("Passenger with ID: {} has a valid ticket", passenger.getId());
                } else {
                    logger.warn("Passenger with ID: {} penalty for without a ticket", passenger.getId());
                }
            }
            logger.info("Checking ticket process finished");
        };

        Thread validatingThread = new Thread(validatingTask, "ValidatingThread");
        Thread checkingThread = new Thread(checkingTask, "CheckingThread");

        validatingThread.start();
        checkingThread.start();

        try {
            validatingThread.join();
            checkingThread.join();
        } catch (InterruptedException e) {
            logger.error("Thread interrupted: {}", e.getMessage());
        }

        logger.info("Ticket process finished");

        System.out.println("My homework 11. Threads using CompletableFuture");

        CompletableFuture<Void> ticketingFuture = CompletableFuture.runAsync(() -> {
            logger.info("Verifying process started");
            passengers.forEach(passenger -> {
                if (passenger.getTicket() == null) {
                    logger.info("Verifying ticket to passenger with ID: {}", passenger.getId());
                    passenger.setTicket(new Ticket(101, LocalDateTime.now(), new BigDecimal("7.00"), null));
                } else {
                    logger.info("Passenger with ID: {} already verified", passenger.getId());
                }
            });
            logger.info("Verifying process successful");
        });

        CompletableFuture<Void> boardingFuture = CompletableFuture.runAsync(() -> {
            logger.info("Boarding process started");
            passengers.forEach(passenger -> {
                if (passenger.getTicket() != null) {
                    logger.info("Passenger with ID: {} boarded the train", passenger.getId());
                } else {
                    logger.warn("Passenger with ID: {} cannot board without a ticket", passenger.getId());
                }
            });
            logger.info("Boarding process successful");
        });

        CompletableFuture<Void> metroOperations = CompletableFuture.allOf(ticketingFuture, boardingFuture);

        try {
            metroOperations.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error during operations: {}", e.getMessage());
        }

        logger.info("Check in operations successful");

        System.out.println("My homework 11. Connection Pool");

        ConnectionPool connectionPool = new ConnectionPool(5);

        ExecutorService threadPool = Executors.newFixedThreadPool(7);

        for (int i = 1; i <= 7; i++) {
            final int threadId = i;
            threadPool.submit(() -> {
                try {
                    logger.info("Thread {} attempting to acquire a connection...", threadId);
                    Connection conn = connectionPool.acquireConnection();
                    logger.info("Thread {} acquired {}", threadId, conn.getName());

                    logger.info(conn.fetchData());
                    Thread.sleep(2000); // Simulate task duration

                    connectionPool.releaseConnection(conn);
                    logger.info("Thread {} released {}", threadId, conn.getName());
                } catch (InterruptedException e) {
                    logger.error("Thread {} was interrupted: {}", threadId, e.getMessage());
            });
        }

        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                logger.warn("Some threads didn't finish within the timeout.");
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error("Main thread interrupted while waiting for thread pool to terminate: {}", e.getMessage());
            threadPool.shutdownNow();
        }

        logger.info("All threads completed");

        passengers.add(new Passenger(20, new Ticket(101, LocalDateTime.now(), new BigDecimal("5.50"), null)));
        passengers.add(new Passenger(31, null));
        passengers.add(new Passenger(42, new Ticket(102, LocalDateTime.now(), new BigDecimal("8.00"), null)));


    }

}
