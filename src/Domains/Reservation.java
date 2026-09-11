package Domains;

import Enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation{
    protected UUID  idRes ;
    protected User idUser ;
    protected Room roomNumber ;
    protected LocalDate checkIn ;
    protected LocalDate checkOut ;
    protected int numberOfGuests ;
    protected long numberOfNights;
    protected BigDecimal totalPrice ;
    protected ReservationStatus status ;
    protected LocalDateTime createdAt;
    Reservation(User idUser , Room roomNumber,LocalDate checkIn , LocalDate checkOut , int numberOfGuests , long numberOfNights , BigDecimal totalPrice , ReservationStatus status , LocalDateTime createdAt){
        this.idRes = UUID.randomUUID();
        this.idUser=idUser;
        this.roomNumber=roomNumber;
        this.checkIn=checkIn;
        this.checkOut=checkOut;
        this.numberOfGuests=numberOfGuests;
        this.numberOfNights=numberOfNights ;
        this.totalPrice=totalPrice;
        this.status = status;
        this.createdAt= createdAt;

    }

    public int getIdRes() {
        return idRes;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public Room getRoomNumber() {
        return roomNumber;
    }

    public User getIdUser() {
        return idUser;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getNumberOfNights() {
        return numberOfNights;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}