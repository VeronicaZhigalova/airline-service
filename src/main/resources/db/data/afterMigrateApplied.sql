
INSERT INTO tickets (date_of_purchase, date_of_flight, date_of_return, seat, price_of_tickets)
VALUES ('2023-01-15', '2024-03-16','2024-05-17','5','1500.60'),
       ('2023-02-20', '2024-04-18', '2024-06-19', '8', '1200.75'),
       ('2023-03-25', '2024-05-27', '2024-07-28', '12', '1800.90'),
       ('2023-04-10', '2024-06-12', '2024-08-13', '3', '1350.40'),
       ('2023-05-15', '2024-07-16', '2024-09-17', '7', '1600.25'),
       ('2023-06-20', '2024-08-22', '2024-10-23', '10', '2000.30'),
       ('2023-07-25', '2024-09-26', '2024-11-27', '2', '1450.50'),
       ('2023-08-30', '2024-10-31', '2025-01-01', '6', '1750.80'),
       ('2023-09-05', '2024-11-06', '2025-01-07', '9', '1900.70'),
       ('2023-10-10', '2024-12-11', '2025-02-12', '4', '1650.95');


INSERT INTO passengers (first_name, last_name, phone_number, email_address)
VALUES  ('Jane', 'Doe', '555-123-4567', 'jane.doe@example.com'),
          ('Robert', 'Johnson', '123-456-7890', 'robert.johnson@example.com'),
          ('Emily', 'Williams', '987-654-3210', 'emily.williams@example.com'),
          ('Michael', 'Davis', '456-789-0123', 'michael.davis@example.com'),
          ('Samantha', 'Miller', '789-012-3456', 'samantha.miller@example.com'),
          ('David', 'Jones', '321-654-9870', 'david.jones@example.com'),
          ('Ella', 'Brown', '234-567-8901', 'ella.brown@example.com'),
          ('William', 'Taylor', '567-890-1234', 'william.taylor@example.com'),
          ('Olivia', 'Anderson', '890-123-4567', 'olivia.anderson@example.com'),
          ('Daniel', 'Thomas', '432-109-8765', 'daniel.thomas@example.com');


INSERT INTO reservations (flight_number, departure_airport, arrival_airport,
departure_time, arrival_time, trip_type, departure, destination, number_of_customer_seats,
class_of_flight, departure_date, return_date, reservation_status, seat_id, passenger_id)
VALUES ('ABC123', 'JFK', 'LAX', '2023-04-15 08:00:00', '2023-04-15 12:00:00', 'One-way', 'New York', 'Los Angeles',
       2, 'Business', '2023-04-15', NULL, 'Confirmed', 1, 101),
      ('XYZ456', 'LHR', 'CDG', '2023-06-20 15:30:00', '2023-06-20 18:30:00', 'Round-trip', 'London', 'Paris',
       1, 'Economy', '2023-06-20', '2023-06-25', 'Pending', 3, 102),
      ('DEF789', 'SFO', 'SEA', '2023-08-10 12:45:00', '2023-08-10 15:30:00', 'One-way', 'San Francisco', 'Seattle',
       3, 'First Class', '2023-08-10', NULL, 'Confirmed', 5, 103),
       ('GHI987', 'HND', 'ICN', '2023-09-25 07:00:00', '2023-09-25 11:00:00', 'Round-trip', 'Tokyo', 'Seoul',
       2, 'Business', '2023-09-25', '2023-09-30', 'Confirmed', 2, 104),
       ('JKL321', 'ORD', 'DFW', '2023-11-05 09:30:00', '2023-11-05 11:45:00', 'One-way', 'Chicago', 'Dallas',
       1, 'Economy', '2023-11-05', NULL, 'Pending', 4, 105),
       ('MNO654', 'SYD', 'AKL', '2024-01-15 14:00:00', '2024-01-15 18:30:00', 'Round-trip', 'Sydney', 'Auckland',
       3, 'First Class', '2024-01-15', '2024-01-20', 'Confirmed', 6, 106),
       ('PQR321', 'ATL', 'MIA', '2024-03-08 11:15:00', '2024-03-08 14:00:00', 'One-way', 'Atlanta', 'Miami',
       2, 'Business', '2024-03-08', NULL, 'Pending', 8, 107),
       ('STU987', 'PEK', 'DEL', '2024-05-20 20:30:00', '2024-05-21 01:45:00', 'Round-trip', 'Beijing', 'Delhi',
       1, 'Economy', '2024-05-20', '2024-05-25', 'Confirmed', 10, 108),
       ('VWX654', 'DXB', 'IST', '2024-07-12 08:45:00', '2024-07-12 13:00:00', 'One-way', 'Dubai', 'Istanbul',
       3, 'First Class', '2024-07-12', NULL, 'Confirmed', 7, 109),
       ('YZA123', 'LAX', 'SFO', '2024-09-30 18:30:00', '2024-09-30 20:45:00', 'Round-trip', 'Los Angeles', 'San Francisco',
       2, 'Business', '2024-09-30', '2024-10-05', 'Pending', 9, 110);


INSERT INTO blocklisted_customers (reason, customer_id)
VALUES ('Fraudulent activity', 1),
       ('Repeated payment failures', 2),
       ('Violation of terms of service', 4),
       ('Suspected account compromise', 6),
       ('Disruptive behavior', 8),
       ('Unauthorized access to system', 10);

INSERT INTO baggages (weight, size, type_of_baggage, reservation_id)
VALUES (14, 'Large', 'Checked', 3),
       (6, 'Medium', 'Carry-on', 5),
       (11, 'Small', 'Checked', 7),
       (4, 'Small', 'Carry-on', 9);

