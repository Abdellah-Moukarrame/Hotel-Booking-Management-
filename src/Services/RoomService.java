package Services;

import Domains.Room;
import java.util.List;

public interface RoomService {

    void getAllRooms();
    List<Room> getAvailableRooms();
    Room getRoomByNumber(String roomNumber);
    void addRoom(Room room);
    void updateRoomStatus(String roomNumber, Enums.RoomStatus status);

}