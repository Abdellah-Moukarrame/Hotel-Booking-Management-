package Domains;

import Enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation{
    protected UUID  idRes ;
    protected String Code ;
    protected User idUser ;
    protected Room roomNumber ;
    protected LocalDate checkIn ;
    protected LocalDate checkOut ;
    protected int numberOfGuests ;
    protected long numberOfNights;
    protected BigDecimal totalPrice ;
    protected ReservationStatus status ;
    protected LocalDateTime createdAt;
    public Reservation(User idUser, Room roomNumber, LocalDate checkIn, LocalDate checkOut, int numberOfGuests, long numberOfNights, BigDecimal totalPrice, ReservationStatus status, LocalDateTime createdAt){
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

    public Reservation(int idU, UUID idR, LocalDate checkIn, LocalDate checkOut) {
    }

    public UUID getIdRes() {
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

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setIdRes(UUID idRes) {
        this.idRes = idRes;
    }

    public void setRoomNumber(Room roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public void setNumberOfNights(long numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public boolean setRoomNumber() {
        return false;
    }
}