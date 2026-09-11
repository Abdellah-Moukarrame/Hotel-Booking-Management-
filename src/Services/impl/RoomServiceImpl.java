package Services.impl;

import Domains.Room;
import Enums.RoomStatus;
import Repository.RoomRepository;
import Repository.impl.RoomRepositoryImpl;
import Services.RoomService;

import java.util.List;
import java.util.stream.Collectors;

public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomRepositoryImpl roomRepositoryimpl ;

    public RoomServiceImpl(RoomRepository roomRepository, RoomRepositoryImpl roomRepositoryimpl) {
        this.roomRepository = roomRepository;

        this.roomRepositoryimpl = roomRepositoryimpl;
    }


    @Override
    public void getAllRooms() {

        for (Room room : roomRepositoryimpl.getRooms().values()) {
            System.out.println(
                    "Room " + room.getRoomNumber()
                            + " | " + room.getType()
                            + " | " + room.getPricePerNight() + "/night"
            );
        }
    }

    @Override
    public List<Room> getAvailableRooms() {
        return  roomRepository.findAll().stream().filter(room -> room.getStatus() == RoomStatus.AVAILABLE).toList();
    }

    @Override
    public Room getRoomByNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber).orElseThrow(()->new IllegalStateException("No room with this roomnumber"));
    }

    @Override
    public void addRoom(Room room) {
        if (roomRepository.findByRoomNumber(room.getRoomNumber()).isPresent()) {
            throw new IllegalStateException("This room is already exested");
        }
        else {
            roomRepository.save(room);
        }
    }

    @Override
    public void updateRoomStatus(String roomNumber, RoomStatus status) {
        Room room = getRoomByNumber(roomNumber);
        room.setStatus(status);
        roomRepository.save(room);
    }
}