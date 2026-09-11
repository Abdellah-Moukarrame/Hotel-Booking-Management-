package Repository.impl;

import Domains.Reservation;
import Repository.ReservationRepository;

import java.util.*;

public class ReservationRepositoryImpl implements ReservationRepository {
    private final Map<UUID,Reservation> reservations = new HashMap<>();


    @Override
    public void save(Reservation reservation) {
        reservations.put(reservation.getIdRes(),reservation);
    }

    @Override
    public Optional<Reservation> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> findByCode(String code) {
        return Optional.empty();
    }

    @Override
    public List<Reservation> findByUserId(int userId) {
        return List.of();
    }

    @Override
    public List<Reservation> findByRoomNumber(String roomNumber) {
        return List.of();
    }

    @Override
    public List<Reservation> findAll() {
        return List.of();
    }
}