package Domains;

import Enums.RoomStatus;
import Enums.RoomType;

import java.math.BigDecimal;
import java.util.UUID;

public class Room{
    protected UUID idR  ;
    protected String roomNumber ;
    protected int capacity ;
    protected BigDecimal pricePerNight;
    protected RoomType Type ;
    protected RoomStatus Status ;

    public Room(String roomNumber, int capacity, BigDecimal pricePerNight, RoomType Type, RoomStatus Status){
        this.idR=UUID.randomUUID();
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.Type = Type;
        this.Status = Status;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public UUID getIdR() {
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

    public void setIdR(UUID idR) {
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
