import Domains.Room;
import Domains.User;
import Repository.RoomRepository;
import Repository.UserRepository;
import Repository.impl.RoomRepositoryImpl;
import Repository.impl.UserRepositoryImpl;
import Services.AuthService;
import Services.RoomService;
import Services.impl.AuthServiceImpl;
import Services.impl.RoomServiceImpl;
import Session.SessionManager;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UserRepository userRepository = new UserRepositoryImpl();

        SessionManager sessionManager = new SessionManager();

        AuthService authService = new AuthServiceImpl(userRepository, sessionManager);

        RoomRepository roomRepository = new RoomRepositoryImpl();
        RoomRepositoryImpl roomRepositoryimpl = new RoomRepositoryImpl();
        RoomService roomService = new RoomServiceImpl(roomRepository, roomRepositoryimpl);


        while (true) {

            System.out.println("""
                    
                    ================= HOTEL BOOKING =================
                    1. Register
                    2. Login
                    3. Logout
                    0. Exit
                    """);

            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();

                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    try {

                        User user = authService.register(
                                name,
                                email,
                                phone,
                                password
                        );

                        System.out.println(
                                "Registration successful: "
                                        + user.getName()
                        );

                    } catch (IllegalArgumentException e) {

                        System.out.println(e.getMessage());
                    }

                    break;


                case 2:

                    System.out.print("Email: ");
                    String loginEmail = scanner.nextLine();

                    System.out.print("Password: ");
                    String loginPassword = scanner.nextLine();

                    try {

                        User user = authService.login(
                                loginEmail,
                                loginPassword
                        );

                        System.out.println(
                                 "\n================================\n" +
                                        "Logged in as:" + user.getName() +"\n" +
                                        "================================\n" +
                                        "1. Search available rooms\n" +
                                        "2. View all rooms\n" +
                                        "3. Create reservation\n" +
                                        "4. My reservations\n" +
                                        "5. Update reservation\n" +
                                        "6. Cancel reservation\n" +
                                        "7. Update profile\n" +
                                        "8. Change password\n" +
                                        "9. Logout\n" +
                                        "0. Exit"
                        );
                        int chioce2 = scanner.nextInt();
                        switch (chioce2){
                            case 1 :
                                System.out.println("Available rooms : \n");
                                List<Room> availableRooms = roomService.getAvailableRooms();
                                if (availableRooms.isEmpty()) {
                                    System.out.println("No rooms available.");
                                } else {
                                    for (Room room : availableRooms) {
                                        System.out.println(
                                                "Room " + room.getRoomNumber()
                                                        + " | " + room.getType()
                                                        + " | " + room.getPricePerNight() + "/night"
                                        );
                                    }
                                }
                                break;
                            case 2 :
                                System.out.println("All rooms : \n");
                                roomService.getAllRooms();
                                break;

                            case 3 :
                                System.out.println("Create reservation : \n");
                                break;

                            case 4 :
                                System.out.println("My reservations : \n");
                                break;

                            case 5 :
                                System.out.println("Update reservation : \n");
                                break;

                            case 6 :
                                System.out.println("Cancel reservation : \n");
                                break;

                            case 7 :
                                System.out.println("Update profile : \n");
                                break;

                            case 8 :
                                System.out.println("Change password : \n");
                                break;

                            case 9 :
                                System.out.println("logout \n");
                                break;

                        }

                    } catch (IllegalArgumentException e) {

                        System.out.println(e.getMessage());
                    }

                    break;


                case 3:

                    authService.logout();

                    System.out.println("Logged out successfully.");

                    break;


                case 0:

                    System.out.println("Goodbye!");

                    scanner.close();

                    return;


                default:

                    System.out.println("Invalid choice.");
            }
        }
    }
}