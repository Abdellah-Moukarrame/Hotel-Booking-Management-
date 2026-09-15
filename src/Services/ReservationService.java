package Services;

import Domains.Reservation;
import Enums.ReservationStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationService {

    Reservation createReservation(UUID idUser, String roomNumber, LocalDate checkIn, LocalDate checkOut, int numberOfGuests);
    boolean isRoomAvailable(String roomNumber, LocalDate checkIn, LocalDate checkOut);
    long calculateNumberOfNights(LocalDate checkIn, LocalDate checkOut);
    BigDecimal calculateTotalPrice(String roomNumber, LocalDate checkIn, LocalDate checkOut);
    Reservation updateReservation(UUID idReservation, LocalDate newCheckIn, LocalDate newCheckOut);
    Reservation getReservationByCode(String code);
    void cancelReservation(UUID idReservation);
    List<Reservation> getReservationsByUser(UUID idUser);

}
