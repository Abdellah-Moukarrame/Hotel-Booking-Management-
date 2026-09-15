package Repository.impl;

import Domains.Reservation;
import Repository.ReservationRepository;

import java.util.*;

public class ReservationRepositoryImpl implements ReservationRepository {

    private final Map<UUID, Reservation> reservations = new HashMap<>();

    @Override
    public void save(Reservation reservation) {
        reservations.put(reservation.getIdRes(),reservation);
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public Optional<Reservation> findByCode(String code) {
        return reservations.values().stream().filter(reservation -> reservation.getCode().equals(code)).findFirst();
    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
        return reservations.values().stream().filter(reservation -> reservation.getIdUser().getIdU().equals(userId)).toList();
    }

    @Override
    public List<Reservation> findByRoomNumber(String roomNumber) {
        return reservations.values().stream().filter(reservation -> reservation.getRoomNumber().getRoomNumber().equals(roomNumber)).toList();
    }



    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }
}
