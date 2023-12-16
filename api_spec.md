# APIs

## Create user
- Method: POST
- Endpoint: /passengers/register
- Request:
   - Request body of 
     - first_name
     - last_name
     - email_address 
     - phone_number
- Response:
HTTP status code

## Available tickets for the given date (passenger)
- Method: GET
- Endpoint: /tickets
- Request:
   - Request (query) parameters
      - date: The date for which to check the availability
- Response:
    - HTTP status code
    - data: Page of tickets objects, each with the following properties:
      - id 
      - date_of_purchase
      - date_of_flight
      - date_of_return
      - seat
      - price_of_tickets

## Create Reservation
- Method: POST
- Endpoint: /reservations/create
- Request:
  - Request body:
    - flight_number 
    - departure_airport  
    - arrival_airport 
    - departure_time 
    - arrival_time 
    - trip_type 
    - from_where 
    - to_where 
    - number_of_customer_seats 
    - class_of_flight 
    - depart_date 
    - return_date 
    - reservation_status  
    - fk_seat_id 
    - fk_passenger_id 
- Response:
  - HTTP status code

## Cancel Reservation
- Method: POST
- Endpoint: /reservations/cancel
- Request:
  - Request body:
    - reservation_id 
- Response:
  - HTTP status code

## Add New Flights
- Method: POST
- Endpoint: /flights/add
- Request:
  - Request body:
    - flight_number
    - departure_airport 
    - arrival_airport 
    - departure_time 
    - arrival_time 
    - status 
- Response:
  - HTTP status code

## Update Flight Schedule
- Method: PUT
- Endpoint: /flights/update/{flight_id}
- Request:
  - Request body:
    - updated_departure_time 
    - updated_arrival_time 
    - updated_status
- Response:
  - HTTP status code

## View List of All Flights
- Method: GET
- Endpoint: /flights
- Response:
  - List of all flights with details

## View List of Available Seats on Each Flight
- Method: GET
- Endpoint: /flights/{flight_id}/available-seats
- Response:
  - List of available seats for the specified flight

## View List of Booked Seats on Each Flight
- Method: GET
- Endpoint: /flights/{flight_id}/booked-seats
- Response:
  - List of booked seats for the specified flight

## View List of Cancelled Reservations
- Method: GET
- Endpoint: /reservations/cancelled
- Response:
  - List of all cancelled reservations

## View List of Upcoming Flights
- Method: GET
- Endpoint: /flights/upcoming
- Response:
  - List of all upcoming flights

## View List of Delayed Flights
- Method: GET
- Endpoint: /flights/delayed
- Response:
  - List of all delayed flights

## View List of Cancelled Flights
- Method: GET
- Endpoint: /flights/cancelled
- Response:
  - List of all cancelled flights

## View List of Completed Flights
- Method: GET
- Endpoint: /flights/completed
- Response:
  - List of all completed flights

## View List of Ongoing Flights
- Method: GET
- Endpoint: /flights/ongoing
- Response:
  - List of all ongoing flights

