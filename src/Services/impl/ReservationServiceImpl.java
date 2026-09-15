package Services.impl;

import Domains.Reservation;
import Domains.Room;
import Domains.User;
import Enums.ReservationStatus;
import Enums.RoomStatus;
import Repository.ReservationRepository;
import Repository.RoomRepository;
import Services.ReservationService;
import Session.SessionManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;


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
    public Reservation createReservation(UUID idUser, String roomNumber, LocalDate checkIn, LocalDate checkOut, int numberOfGuests) {
        User currentUser = requireCurrentUser();
        if (!currentUser.getIdU().equals(idUser)) {
            throw new IllegalArgumentException("You can only book for the logged-in user");
        }
        long nights = calculateNumberOfNights(checkIn, checkOut);
        if (checkIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }

        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        if (numberOfGuests < 1 || numberOfGuests > room.getCapacity()) {
            throw new IllegalArgumentException("Number of guests must be between 1 and " + room.getCapacity());
        }
        if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }

        BigDecimal totalPrice = calculateTotalPrice(roomNumber, checkIn, checkOut);
        Reservation reservation = new Reservation(currentUser, room, checkIn, checkOut,
                numberOfGuests, nights, totalPrice, ReservationStatus.CONFIRMED, LocalDateTime.now());
        reservationRepository.save(reservation);

        return reservation;
    }

    @Override
    public boolean isRoomAvailable(String roomNumber, LocalDate checkIn, LocalDate checkOut) {
        return isRoomAvailableExcept(roomNumber, checkIn, checkOut, null);
    }

    private boolean isRoomAvailableExcept(String roomNumber, LocalDate checkIn, LocalDate checkOut, UUID excludedId) {
        calculateNumberOfNights(checkIn, checkOut);
        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        if (room.getStatus() != RoomStatus.AVAILABLE) {
            return false;
        }

        return reservationRepository.findByRoomNumber(roomNumber).stream()
                .filter(r -> !r.getIdRes().equals(excludedId))
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED)
                .noneMatch(r -> checkIn.isBefore(r.getCheckOut()) && checkOut.isAfter(r.getCheckIn()));
    }

    @Override
    public long calculateNumberOfNights(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkIn.isBefore(checkOut)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        return ChronoUnit.DAYS.between(checkIn, checkOut);
    }

    @Override
    public BigDecimal calculateTotalPrice(String roomNumber, LocalDate checkIn, LocalDate checkOut) {
        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        long nights = calculateNumberOfNights(checkIn, checkOut);

        return room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
    }

    private Reservation requireOwnedReservation(UUID idReservation) {
        User user = requireCurrentUser();
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        if (!reservation.getIdUser().getIdU().equals(user.getIdU())) {
            throw new IllegalArgumentException("This reservation does not belong to you");
        }
        return reservation;
    }

    @Override
    public Reservation getReservationByCode(String code) {
        requireCurrentUser();
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Reservation code is required");
        }
        Reservation reservation = reservationRepository.findByCode(code.trim().toUpperCase(java.util.Locale.ROOT))
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        return requireOwnedReservation(reservation.getIdRes());
    }

    @Override
    public Reservation updateReservation(UUID idReservation, LocalDate newCheckIn, LocalDate newCheckOut) {
        Reservation reservation = requireOwnedReservation(idReservation);
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalArgumentException("Only confirmed reservations can be updated");
        }
        long nights = calculateNumberOfNights(newCheckIn, newCheckOut);
        if (newCheckIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }
        String roomNumber = reservation.getRoomNumber().getRoomNumber();
        if (!isRoomAvailableExcept(roomNumber, newCheckIn, newCheckOut, idReservation)) {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }
        BigDecimal total = calculateTotalPrice(roomNumber, newCheckIn, newCheckOut);
        reservation.setCheckIn(newCheckIn);
        reservation.setCheckOut(newCheckOut);
        reservation.setNumberOfNights(nights);
        reservation.setTotalPrice(total);
        reservationRepository.save(reservation);
        return reservation;
    }

    @Override
    public void cancelReservation(UUID idReservation) {
        Reservation reservation = requireOwnedReservation(idReservation);
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalArgumentException("Only confirmed reservations can be cancelled");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByUser(UUID idUser) {
        User currentUser = requireCurrentUser();
        if (!currentUser.getIdU().equals(idUser)) {
            throw new IllegalArgumentException("You can only view your own reservations");
        }
        return reservationRepository.findByUserId(idUser);
    }
}
