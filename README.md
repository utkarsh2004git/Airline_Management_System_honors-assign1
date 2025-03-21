# Airline Management System

This project consists of three microservices that manages user information , flight information and ticket bookings.

## Architecture Overview

The system consists of two primary microservices:
1. **Users Microservice** - Manages user information and their booked tickets
1. **Flights Microservice** - Manages flight information and schedules
2. **Tickets Microservice** - Handles ticket bookings 

## User Microservice

### Service Details
- **Port**: 5000
- **Context Path**: /user-mgmt
- **Base URL**: http://localhost:5000


### FlightInfo
| Field         | Type          | Description                    |
|---------------|---------------|--------------------------------|
| id            | String        | Unique identifier for user |
| name          | String        | Name of  user              |
| email         | String        | Email of user      |
| address       | String        | Name of user         |
| phone         | String        | Phone of user         |
| bookedTicketIds| List<String> | bookedTickets list of user      |

### API Endpoints

| Method | Endpoint            | Description                           | 
|--------|--------------------|---------------------------------------|
| **POST**   | /users                  | Add a new user                      | 
| **GET**   | /users                  | Get all users                       | 
| **GET**    | /users/{id}              | Get user details by ID              | 
| **PUT**    | /users/{id}/tickets/{ticketId}              | add ticket to ticket-list of user             | 
| **DELETE**    | /users/{id}/tickets/{ticketId}              | remove ticket from ticket-list of user             | 

## Flights Microservice

### Service Details
- **Port**: 5001
- **Context Path**: /flight-mgmt
- **Base URL**: http://localhost:5001


### FlightInfo
| Field         | Type          | Description                    |
|---------------|---------------|--------------------------------|
| flightId            | String        | Unique identifier for the flight |
| flightNumber       | String        | Name of the airline            |
| source        | String        | Departure airport/city         |
| destination   | String        | Arrival airport/city           |
| availableSeats   | Integer        | Arrival airport/city           |
| schedule | ScheduleInfo | Schedule |


### ScheduleInfo
| Field         | Type          | Description                    |
|---------------|---------------|--------------------------------|
| scheduleId            | String        | Unique identifier for the schedule |
| departureTime | LocalDateTime | Scheduled departure time       |
| arrivalTime   | LocalDateTime | Scheduled arrival time         |


### API Endpoints

| Method | Endpoint            | Description                           | Parameters                |
|--------|--------------------|---------------------------------------|---------------------------|
| **POST**   | /flights                 | Add a new flight                      | FlightInfo object in body |
| **GET**    | /flights                 | Get all flights                       | sort (optional): asc/desc to sort by departure time |
| **GET**    | /flights/{id}              | Get flight details by ID              | id: flight ID             |
| **GET**    | /flights/{id}/schedule              | Get flight schedule details by ID              | id: flight ID , dates (optional) : get schedule between given dates           |

## Tickets Microservice

### Service Details
- **Port**: 5002
- **Context Path**: /ticket-mgmt
- **Base URL**: http://localhost:5002


### TicketInfo
| Field          | Type   | Description                      |
|----------------|--------|----------------------------------|
| ticketId       | String | Unique identifier for the ticket |
| userId       | String | Reference to the user          |
| flightId       | String | Reference to the flight          |
| seatNumber     | String | Assigned seat number             |
| price          | double | Price of the ticket              |
| status          | String | Status of the ticket              |
| bookingDate          | LocalDateTime | Booking date of the ticket              |

### API Endpoints

| Method | Endpoint        | Description                      | Parameters                 |
|--------|----------------|----------------------------------|----------------------------|
| **POST**   | /tickets              | Create a new ticket booking      | TicketInfo object in body  |
| **GET**    | /tickets              | Get all tickets                  | None                       |
| **GET**    | /tickets/{id}          | Get ticket details by ID         | id: ticket ID              |
| **DELETE** | /tickets/{id}          | Cancel/delete a ticket           | id: ticket ID              |



## Running the Application
1. Start the **Users** Microservice first
2. Start the **Flights** Microservice second
3. Start the **Tickets** Microservice third
4. Use any REST client (like Postman, cURL, etc.) to interact with the endpoints
