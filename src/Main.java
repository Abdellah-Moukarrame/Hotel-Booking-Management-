import Domains.Room;
import Domains.Reservation;
import Repository.impl.ReservationRepositoryImpl;
import Services.ReservationService;
import Services.impl.ReservationServiceImpl;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        ReservationService reservationService = new ReservationServiceImpl(
                new ReservationRepositoryImpl(), roomRepository, sessionManager);


        while (true) {
            if (sessionManager.isLoggedIn()) {
                User user = sessionManager.getCurrentUser();
                try {
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
                    System.out.print("Choice: ");
                    int chioce2 = Integer.parseInt(scanner.nextLine().trim());
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
                            try {
                                System.out.print("Room number: ");
                                String roomNumber = scanner.nextLine().trim();
                                System.out.print("Check-in (YYYY-MM-DD): ");
                                LocalDate checkIn = LocalDate.parse(scanner.nextLine().trim());
                                System.out.print("Check-out (YYYY-MM-DD): ");
                                LocalDate checkOut = LocalDate.parse(scanner.nextLine().trim());
                                System.out.print("Number of guests: ");
                                int guests = Integer.parseInt(scanner.nextLine().trim());
                                Reservation reservation = reservationService.createReservation(
                                        user.getIdU(), roomNumber, checkIn, checkOut, guests);
                                System.out.println("Reservation confirmed: " + reservation.getCode()
                                        + " | Room " + roomNumber
                                        + " | " + reservation.getNumberOfNights() + " nights"
                                        + " | Total: " + reservation.getTotalPrice());
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid date. Use YYYY-MM-DD, for example 2026-10-01.");
                            } catch (NumberFormatException e) {
                                System.out.println("Number of guests must be a whole number.");
                            }
                            break;

                        case 4 :
                            System.out.println("My reservations : \n");
                            List<Reservation> reservations = reservationService.getReservationsByUser(user.getIdU());
                            if (reservations.isEmpty()) {
                                System.out.println("No reservations found.");
                            }
                            for (Reservation reservation : reservations) {
                                System.out.println(reservation.getCode()
                                        + " | Room " + reservation.getRoomNumber().getRoomNumber()
                                        + " | " + reservation.getCheckIn() + " -> " + reservation.getCheckOut()
                                        + " | Guests: " + reservation.getNumberOfGuests()
                                        + " | Nights: " + reservation.getNumberOfNights()
                                        + " | Total: " + reservation.getTotalPrice()
                                        + " | " + reservation.getStatus());
                            }
                            break;

                        case 5 :
                            System.out.print("Reservation code (from My reservations): ");
                            Reservation toUpdate = reservationService.getReservationByCode(scanner.nextLine());
                            System.out.print("New check-in (YYYY-MM-DD): ");
                            LocalDate newCheckIn = LocalDate.parse(scanner.nextLine().trim());
                            System.out.print("New check-out (YYYY-MM-DD): ");
                            LocalDate newCheckOut = LocalDate.parse(scanner.nextLine().trim());
                            Reservation updated = reservationService.updateReservation(
                                    toUpdate.getIdRes(), newCheckIn, newCheckOut);
                            System.out.println("Reservation updated: " + updated.getCode()
                                    + " | Nights: " + updated.getNumberOfNights()
                                    + " | Total: " + updated.getTotalPrice());
                            break;

                        case 6 :
                            System.out.print("Reservation code (from My reservations): ");
                            Reservation toCancel = reservationService.getReservationByCode(scanner.nextLine());
                            reservationService.cancelReservation(toCancel.getIdRes());
                            System.out.println("Reservation cancelled: " + toCancel.getCode());
                            break;

                        case 7 :
                            System.out.print("New name: ");
                            String newName = scanner.nextLine().trim();
                            System.out.print("New email: ");
                            String newEmail = scanner.nextLine().trim();
                            System.out.print("New phone: ");
                            String newPhone = scanner.nextLine().trim();
                            authService.updateProfile(newName, newEmail, newPhone);
                            System.out.println("Profile updated successfully.");
                            break;

                        case 8 :
                            System.out.print("New password: ");
                            String newPassword = scanner.nextLine();
                            System.out.print("Confirm new password: ");
                            String confirmation = scanner.nextLine();
                            if (!newPassword.equals(confirmation)) {
                                throw new IllegalArgumentException("Passwords do not match");
                            }
                            authService.updatePassword(newPassword);
                            System.out.println("Password changed successfully.");
                            break;

                        case 0 :
                            System.out.println("Goodbye!");
                            scanner.close();
                            return;

                        case 9 :
                            authService.logout();
                            System.out.println("Logged out successfully.");
                            break;

                        default:
                            System.out.println("Invalid choice.");
                    }

                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date. Use YYYY-MM-DD.");
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println(e.getMessage());
                }
                continue;
            }

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

                        authService.login(
                                loginEmail,
                                loginPassword
                        );


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
