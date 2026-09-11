package Repository.impl;

import Domains.Room;
import Enums.RoomStatus;
import Enums.RoomType;
import Repository.RoomRepository;

import java.math.BigDecimal;
import java.util.*;

public class RoomRepositoryImpl implements RoomRepository {
    private final Map<UUID,Room> rooms = new HashMap<>();
    {
        Room r1 = new Room("101", 2, new BigDecimal("50.00"), RoomType.SINGLE, RoomStatus.AVAILABLE);
        Room r2 = new Room("102", 2, new BigDecimal("70.00"), RoomType.DOUBLE, RoomStatus.AVAILABLE);
        Room r3 = new Room("201", 4, new BigDecimal("120.00"), RoomType.SUITE, RoomStatus.AVAILABLE);
        Room r4 = new Room("202", 2, new BigDecimal("65.00"), RoomType.DOUBLE, RoomStatus.MAINTENANCE);

        rooms.put(r1.getIdR(), r1);
        rooms.put(r2.getIdR(), r2);
        rooms.put(r3.getIdR(), r3);
        rooms.put(r4.getIdR(), r4);
    }
    @Override
    public void save(Room room) {

        rooms.put(room.getIdR(),room);
    }

    @Override
    public Optional<Room> findByRoomNumber(String roomNumber) {
        return rooms.values().stream().filter(room -> roomNumber.equals(room.getRoomNumber())).findFirst();
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(rooms.values());
    }

    public Map<UUID, Room> getRooms() {
        return rooms;
    }
}