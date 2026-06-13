# Hotel Management System

A Java-based console application designed to manage hotel room reservations efficiently using JDBC and MySQL database integration. The system allows users to perform complete reservation management operations through a menu-driven interface.

---

## Features

* Reserve hotel rooms
* View all reservations
* Search room number using reservation ID
* Update reservation details
* Delete reservations
* MySQL database integration using JDBC
* Input validation for better user experience
* Structured console-based UI

---

## Technologies Used

* Java
* JDBC
* MySQL
* VS Code
* MySQL Connector/J

---

## Database Schema

### Table: `reservations`

| Column Name      | Data Type         |
| ---------------- | ----------------- |
| reservation_id   | INT (Primary Key) |
| guest_name       | VARCHAR(100)      |
| room_number      | INT               |
| contact_number   | VARCHAR(15)       |
| email            | VARCHAR(100)      |
| room_type        | VARCHAR(50)       |
| status           | VARCHAR(20)       |
| reservation_date | TIMESTAMP         |

---

## Project Structure

```text
HotelManagementSystem
│
├── lib
│   └── mysql-connector-j-9.7.0.jar
│
├── src
│   └── HotelReservationSystem.java
│
├── README.md
└── .gitignore
```

---

## Setup Instructions

### 1. Clone Repository

```bash
git clone YOUR_GITHUB_REPO_LINK
```

### 2. Create MySQL Database

```sql
CREATE DATABASE hotel_db;
USE hotel_db;
```

### 3. Create Reservations Table

```sql
CREATE TABLE reservations (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    guest_name VARCHAR(100),
    room_number INT,
    contact_number VARCHAR(15),
    email VARCHAR(100),
    room_type VARCHAR(50),
    status VARCHAR(20),
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### 4. Compile the Project

```bash
javac -cp "../lib/mysql-connector-j-9.7.0.jar" *.java
```

### 5. Run the Project

```bash
java -cp ".:../lib/mysql-connector-j-9.7.0.jar" HotelReservationSystem
```

---

## Application Modules

### Reserve Room

Allows users to create a new reservation by entering:

* Guest details
* Room number
* Contact information
* Room type

### View Reservations

Displays all reservation records stored in the database.

### Update Reservation

Updates reservation details using reservation ID.

### Delete Reservation

Removes reservation details from the database.

### Get Room Number

Retrieves room number using reservation ID and guest name.

---

## Future Enhancements

* Spring Boot REST API integration
* User authentication system
* GUI interface using JavaFX
* Payment gateway integration
* Online booking support

---

## Learning Outcomes

This project helped in understanding:

* JDBC connectivity
* CRUD operations
* SQL query execution
* Exception handling
* Java OOP concepts
* Database management

---

## Author

Diptadeep Sarkar

---
