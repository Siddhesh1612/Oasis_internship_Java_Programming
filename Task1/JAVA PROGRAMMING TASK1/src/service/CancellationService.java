package service;

import java.io.IOException;

import data.ReservationStore;
import model.Reservation;

public class CancellationService {

    private final ReservationStore reservationStore;

    public CancellationService() {
        reservationStore = new ReservationStore();
    }

    public Reservation getReservationByPnr(String pnr) throws IOException {
        if (pnr == null || pnr.trim().isEmpty()) {
            throw new IllegalArgumentException("PNR number is required.");
        }

        Reservation reservation = reservationStore.findReservationByPnr(pnr.trim());

        if (reservation == null) {
            throw new IllegalArgumentException("No reservation found for the entered PNR.");
        }

        return reservation;
    }

    public Reservation cancelReservation(String pnr) throws IOException {
        Reservation reservation = getReservationByPnr(pnr);

        if ("Cancelled".equalsIgnoreCase(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("This reservation is already cancelled.");
        }

        reservation.cancelReservation();
        reservationStore.updateReservation(reservation);
        return reservation;
    }
}
