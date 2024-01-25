CREATE TABLE tickets
(
id SERIAL PRIMARY KEY,
date_of_purchase DATE NOT NULL,
date_of_flight DATE NOT NULL,
date_of_return DATE NOT NULL,
seat VARCHAR(10) NOT NULL,
price_of_ticket DECIMAL(10, 2) NOT NULL
);

CREATE TABLE passengers
(
id SERIAL PRIMARY KEY,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
phone_number VARCHAR(30) NOT NULL,
email_address VARCHAR(255) NOT NULL
);

CREATE TABLE blocklisted_customers
(
id SERIAL PRIMARY KEY,
reason VARCHAR(255) NOT NULL,
customer_id INTEGER NOT NULL,
CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES passengers (id) ON DELETE CASCADE
);

CREATE TABLE reservations
(
id SERIAL PRIMARY KEY,
flight_number VARCHAR(20) NOT NULL,
departure_airport VARCHAR(50) NOT NULL,
arrival_airport VARCHAR(50) NOT NULL,
departure_time TIMESTAMP NOT NULL,
arrival_time TIMESTAMP NOT NULL,
trip_type VARCHAR(10) NOT NULL,
departure VARCHAR(50) NOT NULL,
destination VARCHAR(50) NOT NULL,
number_of_customer_seats INTEGER NOT NULL,
class_of_flight VARCHAR(20) NOT NULL,
departure_date DATE NOT NULL,
return_date DATE,
reservation_status VARCHAR(20) NOT NULL,
fk_ticket_id INTEGER NOT NULL,
fk_passenger_id INTEGER NOT NULL,
CONSTRAINT fk_ticket_id FOREIGN KEY (fk_ticket_id) REFERENCES tickets (id) ON DELETE CASCADE,
CONSTRAINT fk_passenger_id FOREIGN KEY (fk_passenger_id) REFERENCES passengers (id) ON DELETE CASCADE
);


CREATE TABLE baggages
(
id SERIAL PRIMARY KEY,
weight INTEGER NOT NULL,
size INTEGER NOT NULL,
type_of_baggage VARCHAR(50) NOT NULL,
reservation_id INTEGER,
CONSTRAINT fk_reservation_id FOREIGN KEY(reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
);