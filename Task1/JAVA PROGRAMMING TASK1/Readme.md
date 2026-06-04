# Online Reservation System

This project is a Java Swing based Online Reservation System made using Object-Oriented Layered Architecture. It is a simple diploma-level railway reservation project that allows a user to log in, reserve a ticket, and cancel a reservation using a PNR number.

## Project Objective

The main objective of this project is to simulate a basic railway reservation workflow in a structured and user-friendly way.

Workflow:

Login  
-> Dashboard  
-> Reservation or Cancellation  
-> Business Processing  
-> Data Storage  
-> Result Display

## Features

1. Login with user ID and password
2. Ticket reservation with passenger and journey details
3. Automatic train name based on train number
4. Unique PNR generation
5. Reservation data saved in `reservations.txt`
6. Reservation search by PNR
7. Reservation cancellation with confirmation
8. Error messages for invalid input

## Technologies Used

1. Java
2. Swing GUI
3. File Handling
4. Classes and Objects
5. Methods and Constructors
6. Exception Handling
7. Encapsulation

## Project Structure

### Presentation Layer

1. `src/ui/LoginFrame.java`
2. `src/ui/ReservationFrame.java`
3. `src/ui/CancellationFrame.java`

### Business Logic Layer

1. `src/service/LoginService.java`
2. `src/service/ReservationService.java`
3. `src/service/CancellationService.java`
4. `src/service/TrainService.java`

### Data Layer

1. `src/data/ReservationStore.java`
2. `src/data/reservations.txt`

### Core Class

1. `src/model/Reservation.java`

## Default Login

Use the following login details:

1. Login ID: `user`
2. Password: `1234`

## How to Run

Open PowerShell in the project folder and run:

```powershell
cd "c:\Professional docs\Oasis Infobyte Internship\Oasis_JavaProgramming_Internship\JAVA PROGRAMMING TASK1\src"
javac Main.java ui\*.java service\*.java data\*.java model\*.java
java Main
```

## How the System Works

### 1. Login Module

The user enters login ID and password. If both values are correct, the dashboard opens. Otherwise, an invalid login message is shown.

### 2. Reservation Module

The user fills:

1. Passenger Name
2. Train Number
3. Class Type
4. Date of Journey
5. Source
6. Destination

Then the system:

1. Finds the train name automatically
2. Validates the entered data
3. Generates a unique PNR
4. Stores the reservation in `reservations.txt`
5. Displays the reservation details

### 3. Cancellation Module

The user enters a PNR number. If the reservation exists, its details are displayed. After confirmation, the reservation status is changed to `Cancelled`.

## Sample Reservation Record Format

Each reservation is stored in the text file in this format:

```text
Passenger Name|Train Number|Train Name|Class Type|Date of Journey|Source|Destination|PNR|Reservation Status
```

## Code Quality and Simplicity

This project is kept simple and beginner-friendly:

1. Separate layers for UI, business logic, and data
2. Easy-to-read class names and method names
3. No database or external libraries
4. Simple file-based storage
5. Direct and clear Swing forms

## Conclusion

This project satisfies the required modules of an Online Reservation System using Java. It is simple, complete, and suitable for academic evaluation because it demonstrates GUI design, object-oriented programming, file handling, validation, and layered architecture in a clear way.


