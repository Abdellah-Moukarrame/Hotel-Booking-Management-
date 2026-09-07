package Domains;

import Enums.RoomStatus;
import Enums.RoomType;

import java.math.BigDecimal;

public class Room{
    protected int idR = 0 ;
    protected String roomNumber ;
    protected int capacity ;
    protected BigDecimal pricePerNight;
    protected RoomType Type ;
    protected RoomStatus Status ;

    Room(String roomNumber , int capacity , BigDecimal pricePerNight , RoomType Type , RoomStatus Status){
        roomNumber = roomNumber ;
        capacity = capacity ;
        pricePerNight = pricePerNight ;
        Type = Type ;
        Status = Status ;
        idR++ ;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getIdR() {
        return idR;
    }

    public RoomStatus getStatus() {
        return Status;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return Type;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setStatus(RoomStatus status) {
        Status = status;
    }

    public void setType(RoomType type) {
        Type = type;
    }
}
