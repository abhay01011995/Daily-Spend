# DailySpend Backend Service

A Spring Boot microservice for the DailySpend application that manages user accounts, transactions, and membership plans.

## Features

### Account Management
- **Profile Information**: Name (mandatory), email address, mobile number (mandatory), date of birth, gender
- **Transaction Tracking**: Total transactions, current month transactions
- **Bill Management**: Total bills, current month bills  
- **Membership Plans**: Support for different membership tiers (Basic, Silver, Gold, Platinum)
- **Profile Completion**: Automatic calculation of profile completion percentage
- **Currency Support**: Multi-currency support (default: INR)

### API Endpoints

#### Account Endpoints
- `POST /api/v1/accounts` - Create new account
- `PUT /api/v1/accounts/{accountId}` - Update account
- `GET /api/v1/accounts/{accountId}` - Get account by ID
- `GET /api/v1/accounts/email/{emailAddress}` - Get account by email
- `GET /api/v1/accounts/mobile/{mobileNumber}` - Get account by mobile number
- `GET /api/v1/accounts` - Get all accounts
- `DELETE /api/v1/accounts/{accountId}` - Delete account
- `GET /api/v1/accounts/health` - Health check

#### Membership Plan Endpoints
- `GET /api/v1/membership-plans` - Get all membership plans
- `GET /api/v1/membership-plans/active` - Get active membership plans
- `GET /api/v1/membership-plans/{planId}` - Get membership plan by ID
- `GET /api/v1/membership-plans/type/{planType}` - Get plans by type

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **MySQL 8**
- **Maven**
- **Bean Validation**

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Setup Instructions

### 1. Database Setup
```sql
-- Create MySQL database
CREATE DATABASE dailyspend_db;

-- Create MySQL user (optional)
CREATE USER 'dailyspend_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON dailyspend_db.* TO 'dailyspend_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Application Configuration
Update `src/main/resources/application.yml` with your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dailyspend_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```

### 3. Build and Run

#### Using Maven
```bash
# Build the application
mvn clean compile

# Run the application
mvn spring-boot:run
```

#### Using Maven Wrapper
```bash
# Build the application
./mvnw clean compile

# Run the application
./mvnw spring-boot:run
```

#### Using JAR
```bash
# Build JAR file
mvn clean package

# Run the JAR
java -jar target/dailyspend-backend-1.0.0.jar
```

### 4. Verify Installation
The application will start on `http://localhost:8080`

Test the health endpoint:
```bash
curl http://localhost:8080/api/v1/accounts/health
```

## Sample API Usage

### Create Account
```bash
curl -X POST http://localhost:8080/api/v1/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Abhay",
    "emailAddress": "abhayshivhare19@gmail.com",
    "mobileNumber": "8959388304",
    "dateOfBirth": "1995-01-01",
    "gender": "MALE",
    "currencyCode": "INR",
    "membershipPlanId": 2
  }'
```

### Get Account
```bash
curl http://localhost:8080/api/v1/accounts/1
```

### Get Membership Plans
```bash
curl http://localhost:8080/api/v1/membership-plans/active
```

## Database Schema

### Tables Created
- `accounts` - User account information
- `membership_plans` - Available membership plans
- `transactions` - User transactions and bills

### Sample Data
The application automatically creates sample membership plans:
- Basic Monthly Plan (₹99/month)
- Silver Quarterly Plan (₹249/quarter)
- Gold Half Yearly Plan (₹449/6 months)
- Platinum Yearly Plan (₹799/year)

## Validation Rules

- **Name**: Mandatory field
- **Mobile Number**: Mandatory, must be exactly 10 digits
- **Email**: Must be valid email format
- **Mobile & Email**: Must be unique across all accounts

## Profile Completion Calculation

Profile completion percentage is calculated based on:
- Name (mandatory)
- Email address
- Mobile number (mandatory)
- Date of birth
- Gender
- Membership plan

## Error Handling

The API returns appropriate HTTP status codes:
- `200 OK` - Successful operation
- `201 Created` - Resource created successfully
- `400 Bad Request` - Validation errors or duplicate data
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server errors

## Development

### Project Structure
```
src/
├── main/
│   ├── java/com/dailyspend/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # JPA entities
│   │   ├── repository/     # Data repositories
│   │   └── service/        # Business logic
│   └── resources/
│       └── application.yml  # Application configuration
└── test/                   # Test files
```

### Adding New Features
1. Create entity in `entity/` package
2. Create repository in `repository/` package
3. Create DTOs in `dto/` package
4. Implement service in `service/` package
5. Create controller in `controller/` package

## Future Enhancements
- Transaction management endpoints
- Bill tracking functionality
- Payment integration
- User authentication and authorization
- Email notifications
- Mobile app integration
- Analytics and reporting