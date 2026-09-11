package Repository;

import Domains.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    UUID save(Reservation reservation);
    Optional<Reservation> findById(int id);
    Optional<Reservation> findByCode(String code);
    List<Reservation> findByUserId(int userId);
    List<Reservation> findByRoomNumber(String roomNumber);
    List<Reservation> findAll();
}