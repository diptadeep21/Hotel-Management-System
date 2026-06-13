
import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {

    private static final String URL = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Scanner scanner = new Scanner(System.in);

            while (true) {

                System.out.println("\n====================================");
                System.out.println("      HOTEL MANAGEMENT SYSTEM");
                System.out.println("====================================");
                System.out.println(" Developed using Java + JDBC + MySQL");
                System.out.println("====================================");

                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");

                System.out.print("\nEnter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {

                    case 1:
                        reserveRoom(connection, scanner);
                        break;

                    case 2:
                        viewReservations(connection);
                        break;

                    case 3:
                        getRoomNumber(connection, scanner);
                        break;

                    case 4:
                        updateReservation(connection, scanner);
                        break;

                    case 5:
                        deleteReservation(connection, scanner);
                        break;

                    case 0:
                        exitSystem();
                        scanner.close();
                        connection.close();
                        return;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }

        } catch (Exception exception) {
            System.out.println("Database connection failed!");
            exception.printStackTrace();
        }
    }

    public static void reserveRoom(Connection connection, Scanner scanner) {

        try {

            scanner.nextLine();

            System.out.print("Enter Guest Name: ");
            String guestName = scanner.nextLine();

            if (guestName.isEmpty()) {
                System.out.println("Guest name cannot be empty!");
                return;
            }

            System.out.print("Enter Room Number: ");
            int roomNumber = scanner.nextInt();

            if (roomNumber <= 0) {
                System.out.println("Invalid room number!");
                return;
            }

            scanner.nextLine();

            System.out.print("Enter Contact Number: ");
            String contactNumber = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
            String roomType = scanner.nextLine();

            String status = "Confirmed";

            String query = "INSERT INTO reservations " +
                    "(guest_name, room_number, contact_number, email, room_type, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, guestName);
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.setString(3, contactNumber);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, roomType);
            preparedStatement.setString(6, status);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("\nReservation completed successfully!");
            } else {
                System.out.println("\nReservation failed!");
            }

        } catch (SQLException exception) {
            System.out.println("Error while reserving room!");
            exception.printStackTrace();
        }
    }

    public static void viewReservations(Connection connection) {

        String query = "SELECT * FROM reservations";

        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("\n================ CURRENT RESERVATIONS ================");

            while (resultSet.next()) {

                System.out.println("------------------------------------------");
                System.out.println("Reservation ID : " + resultSet.getInt("reservation_id"));
                System.out.println("Guest Name     : " + resultSet.getString("guest_name"));
                System.out.println("Room Number    : " + resultSet.getInt("room_number"));
                System.out.println("Contact Number : " + resultSet.getString("contact_number"));
                System.out.println("Email          : " + resultSet.getString("email"));
                System.out.println("Room Type      : " + resultSet.getString("room_type"));
                System.out.println("Status         : " + resultSet.getString("status"));
                System.out.println("Reservation On : " + resultSet.getTimestamp("reservation_date"));
            }

            System.out.println("======================================================");

        } catch (SQLException exception) {
            System.out.println("Unable to fetch reservations!");
            exception.printStackTrace();
        }
    }

    public static void getRoomNumber(Connection connection, Scanner scanner) {

        try {

            System.out.print("Enter Reservation ID: ");
            int reservationId = scanner.nextInt();

            scanner.nextLine();

            System.out.print("Enter Guest Name: ");
            String guestName = scanner.nextLine();

            String query = "SELECT room_number FROM reservations " +
                    "WHERE reservation_id = ? AND guest_name = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, reservationId);
            preparedStatement.setString(2, guestName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int roomNumber = resultSet.getInt("room_number");

                System.out.println("\nRoom Number: " + roomNumber);

            } else {
                System.out.println("\nReservation not found!");
            }

        } catch (SQLException exception) {
            System.out.println("Error fetching room number!");
            exception.printStackTrace();
        }
    }

    public static void updateReservation(Connection connection, Scanner scanner) {

        try {

            System.out.print("Enter Reservation ID to update: ");
            int reservationId = scanner.nextInt();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found!");
                return;
            }

            scanner.nextLine();

            System.out.print("Enter New Guest Name: ");
            String newGuestName = scanner.nextLine();

            System.out.print("Enter New Room Number: ");
            int newRoomNumber = scanner.nextInt();

            scanner.nextLine();

            System.out.print("Enter New Contact Number: ");
            String newContactNumber = scanner.nextLine();

            String query = "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? " +
                    "WHERE reservation_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, newGuestName);
            preparedStatement.setInt(2, newRoomNumber);
            preparedStatement.setString(3, newContactNumber);
            preparedStatement.setInt(4, reservationId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("\nReservation updated successfully!");
            } else {
                System.out.println("\nReservation update failed!");
            }

        } catch (SQLException exception) {
            System.out.println("Error updating reservation!");
            exception.printStackTrace();
        }
    }

    public static void deleteReservation(Connection connection, Scanner scanner) {

        try {

            System.out.print("Enter Reservation ID to delete: ");
            int reservationId = scanner.nextInt();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found!");
                return;
            }

            String query = "DELETE FROM reservations WHERE reservation_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, reservationId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("\nReservation deleted successfully!");
            } else {
                System.out.println("\nReservation deletion failed!");
            }

        } catch (SQLException exception) {
            System.out.println("Error deleting reservation!");
            exception.printStackTrace();
        }
    }

    public static boolean reservationExists(Connection connection, int reservationId) {

        try {

            String query = "SELECT reservation_id FROM reservations WHERE reservation_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, reservationId);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static void exitSystem() throws InterruptedException {

        System.out.print("\nExiting System");

        for (int i = 0; i < 5; i++) {
            System.out.print(" .");
            Thread.sleep(400);
        }

        System.out.println("\nThank you for using Hotel Management System!");
    }
}

