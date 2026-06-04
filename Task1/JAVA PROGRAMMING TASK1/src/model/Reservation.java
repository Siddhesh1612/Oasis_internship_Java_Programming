package model;

import java.util.Random;

public class Reservation {

    private static final Random RANDOM = new Random();

    private String passengerName;
    private String trainNumber;
    private String trainName;
    private String classType;
    private String dateOfJourney;
    private String source;
    private String destination;
    private String pnr;
    private String reservationStatus;

    public Reservation(String passengerName, String trainNumber, String trainName, String classType,
            String dateOfJourney, String source, String destination) {
        this(passengerName, trainNumber, trainName, classType, dateOfJourney, source, destination, null, "Confirmed");
    }

    public Reservation(String passengerName, String trainNumber, String trainName, String classType,
            String dateOfJourney, String source, String destination, String pnr, String reservationStatus) {
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.source = source;
        this.destination = destination;
        this.pnr = pnr;
        this.reservationStatus = reservationStatus;

        if (this.pnr == null || this.pnr.trim().isEmpty()) {
            generatePNR();
        }
    }

    public String generatePNR() {
        long timestampPart = System.currentTimeMillis() % 1_000_000L;
        int randomPart = 100 + RANDOM.nextInt(900);
        pnr = "PNR" + timestampPart + randomPart;
        return pnr;
    }

    public String displayReservation() {
        return "PNR: " + pnr
                + "\nPassenger Name: " + passengerName
                + "\nTrain Number: " + trainNumber
                + "\nTrain Name: " + trainName
                + "\nClass Type: " + classType
                + "\nDate of Journey: " + dateOfJourney
                + "\nSource: " + source
                + "\nDestination: " + destination
                + "\nReservation Status: " + reservationStatus;
    }

    public void cancelReservation() {
        reservationStatus = "Cancelled";
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getClassType() {
        return classType;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getPnr() {
        return pnr;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }
}
