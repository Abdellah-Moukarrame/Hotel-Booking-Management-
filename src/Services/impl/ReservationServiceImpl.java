package Services.impl;

import Domains.Reservation;
import Domains.Room;
import Domains.User;
import Enums.ReservationStatus;
import Repository.ReservationRepository;
import Repository.RoomRepository;
import Services.ReservationService;
import Session.SessionManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final SessionManager sessionManager;
    public ReservationServiceImpl(ReservationRepository reservationRepository , RoomRepository roomRepository , SessionManager sessionManager ) {
        this.reservationRepository = reservationRepository ;
        this.roomRepository=roomRepository;
        this.sessionManager =sessionManager;
    }
    private User requireCurrentUser() {
        User user = sessionManager.getCurrentUser();
        if (user == null) {
            throw new IllegalStateException("No user logged in");
        }
        return user;
    }

    @Override
    public Reservation createReservation(int idUser, String roomNumber, LocalDate checkIn, LocalDate checkOut) {
        User currentUser = requireCurrentUser();

        if (checkIn == null || checkOut == null || !checkIn.isBefore(checkOut)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }

        Reservation reservation = new Reservation(currentUser.getIdU(), room.getIdR(), checkIn, checkOut);
        reservationRepository.save(reservation);

        return reservation;
    }

    @Override
    public boolean isRoomAvailable(String roomNumber, LocalDate checkIn, LocalDate checkOut) {
        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        return false;
    }

    @Override
    public long calculateNumberOfNights(LocalDate checkIn, LocalDate checkOut) {
        return 0;
    }

    @Override
    public BigDecimal calculateTotalPrice(String roomNumber, LocalDate checkIn, LocalDate checkOut) {
        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        long nights = calculateNumberOfNights(checkIn, checkOut);

        return room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
    }

    @Override
    public Reservation updateReservation(int idReservation, LocalDate newCheckIn, LocalDate newCheckOut) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (newCheckIn == null || newCheckOut == null || !newCheckIn.isBefore(newCheckOut)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        reservation.setCheckIn(newCheckIn);
        reservation.setCheckOut(newCheckOut);

        reservationRepository.save(reservation);

        return reservation;
    }

    @Override
    public void cancelReservation(int idReservation) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        reservation.setStatus(ReservationStatus.CANCELLED);

        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByUser(int idUser) {
        User currentUser = requireCurrentUser();
        return reservationRepository.findAll().stream()
                .filter(r -> r.getIdUser().getIdU() == currentUser.getIdU())
                .collect(Collectors.toList());
    }

}