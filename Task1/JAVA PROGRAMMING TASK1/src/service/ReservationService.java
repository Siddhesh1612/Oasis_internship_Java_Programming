package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import data.ReservationStore;
import model.Reservation;

public class ReservationService {

    private final ReservationStore reservationStore;
    private final TrainService trainService;

    public ReservationService() {
        reservationStore = new ReservationStore();
        trainService = new TrainService();
    }

    public Reservation reserveTicket(String passengerName, String trainNumber, String classType,
            String dateOfJourney, String source, String destination) throws IOException {
        validateReservationInput(passengerName, trainNumber, classType, dateOfJourney, source, destination);

        Reservation reservation = new Reservation(
                passengerName.trim(),
                trainNumber.trim(),
                trainService.getTrainName(trainNumber),
                classType.trim(),
                dateOfJourney.trim(),
                source.trim(),
                destination.trim());

        reservationStore.saveReservation(reservation);
        return reservation;
    }

    public TrainService getTrainService() {
        return trainService;
    }

    private void validateReservationInput(String passengerName, String trainNumber, String classType,
            String dateOfJourney, String source, String destination) {
        if (passengerName == null || passengerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name is required.");
        }

        if (trainNumber == null || trainNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Train number is required.");
        }

        if (!trainService.isValidTrainNumber(trainNumber)) {
            throw new IllegalArgumentException("Please select a valid train number.");
        }

        if (classType == null || classType.trim().isEmpty()) {
            throw new IllegalArgumentException("Class type is required.");
        }

        validateJourneyDate(dateOfJourney);

        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source is required.");
        }

        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination is required.");
        }

        if (source.trim().equalsIgnoreCase(destination.trim())) {
            throw new IllegalArgumentException("Source and destination cannot be the same.");
        }
    }

    private void validateJourneyDate(String dateOfJourney) {
        if (dateOfJourney == null || dateOfJourney.trim().isEmpty()) {
            throw new IllegalArgumentException("Date of journey is required.");
        }

        try {
            LocalDate journeyDate = LocalDate.parse(dateOfJourney.trim());

            if (journeyDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Date of journey cannot be in the past.");
            }
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Enter date in YYYY-MM-DD format.");
        }
    }
}
