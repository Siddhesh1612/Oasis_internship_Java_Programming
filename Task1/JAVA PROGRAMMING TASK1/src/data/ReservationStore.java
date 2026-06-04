package data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import model.Reservation;

public class ReservationStore {

    private static final String SEPARATOR = "\\|";

    private final Path reservationFilePath;

    public ReservationStore() {
        reservationFilePath = resolveReservationFilePath();
        ensureFileExists();
    }

    public void saveReservation(Reservation reservation) throws IOException {
        ensureFileExists();

        try (BufferedWriter writer = Files.newBufferedWriter(
                reservationFilePath,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write(convertToLine(reservation));
            writer.newLine();
        }
    }

    public Reservation findReservationByPnr(String pnr) throws IOException {
        List<Reservation> reservations = loadAllReservations();

        for (Reservation reservation : reservations) {
            if (reservation.getPnr().equalsIgnoreCase(pnr)) {
                return reservation;
            }
        }

        return null;
    }

    public void updateReservation(Reservation updatedReservation) throws IOException {
        List<Reservation> reservations = loadAllReservations();

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getPnr().equalsIgnoreCase(updatedReservation.getPnr())) {
                reservations.set(i, updatedReservation);
                rewriteReservations(reservations);
                return;
            }
        }
    }

    private List<Reservation> loadAllReservations() throws IOException {
        ensureFileExists();

        List<Reservation> reservations = new ArrayList<>();
        List<String> lines = Files.readAllLines(reservationFilePath, StandardCharsets.UTF_8);

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                try {
                    reservations.add(convertToReservation(line));
                } catch (IllegalArgumentException exception) {
                    // Ignore invalid records so the program can continue to run.
                }
            }
        }

        return reservations;
    }

    private void rewriteReservations(List<Reservation> reservations) throws IOException {
        ensureFileExists();

        try (BufferedWriter writer = Files.newBufferedWriter(
                reservationFilePath,
                StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {
            for (Reservation reservation : reservations) {
                writer.write(convertToLine(reservation));
                writer.newLine();
            }
        }
    }

    private Reservation convertToReservation(String line) {
        String[] parts = line.split(SEPARATOR, -1);

        if (parts.length < 9) {
            throw new IllegalArgumentException("Invalid reservation record found in file.");
        }

        return new Reservation(
                parts[0],
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                parts[6],
                parts[7],
                parts[8]);
    }

    private String convertToLine(Reservation reservation) {
        return sanitize(reservation.getPassengerName()) + "|"
                + sanitize(reservation.getTrainNumber()) + "|"
                + sanitize(reservation.getTrainName()) + "|"
                + sanitize(reservation.getClassType()) + "|"
                + sanitize(reservation.getDateOfJourney()) + "|"
                + sanitize(reservation.getSource()) + "|"
                + sanitize(reservation.getDestination()) + "|"
                + sanitize(reservation.getPnr()) + "|"
                + sanitize(reservation.getReservationStatus());
    }

    private String sanitize(String value) {
        return value.replace("|", "/");
    }

    private Path resolveReservationFilePath() {
        Path runFromSourceFolder = Paths.get("data", "reservations.txt");
        Path runFromProjectFolder = Paths.get("src", "data", "reservations.txt");

        if (Files.exists(runFromSourceFolder.getParent())) {
            return runFromSourceFolder;
        }

        return runFromProjectFolder;
    }

    private void ensureFileExists() {
        try {
            if (reservationFilePath.getParent() != null) {
                Files.createDirectories(reservationFilePath.getParent());
            }

            if (!Files.exists(reservationFilePath)) {
                Files.createFile(reservationFilePath);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Unable to access reservation storage.", exception);
        }
    }
}
