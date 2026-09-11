package Services;

import Domains.Reservation;
import Enums.ReservationStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    Reservation createReservation(int idUser, String roomNumber, LocalDate checkIn, LocalDate checkOut);
    boolean isRoomAvailable(String roomNumber, LocalDate checkIn, LocalDate checkOut);
    long calculateNumberOfNights(LocalDate checkIn, LocalDate checkOut);
    BigDecimal calculateTotalPrice(String roomNumber, LocalDate checkIn, LocalDate checkOut);
    Reservation updateReservation(int idReservation, LocalDate newCheckIn, LocalDate newCheckOut);
    void cancelReservation(int idReservation);
    List<Reservation> getReservationsByUser(int idUser);

}