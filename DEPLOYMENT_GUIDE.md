# DailySpend Backend - Deployment Guide

## Overview
This guide provides multiple ways to deploy and run the DailySpend Backend service.

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (for local development)
- Docker & Docker Compose (for containerized deployment)

## Deployment Options

### 1. Local Development (with external MySQL)

#### Setup MySQL Database
```sql
CREATE DATABASE dailyspend_db;
CREATE USER 'dailyspend_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON dailyspend_db.* TO 'dailyspend_user'@'localhost';
FLUSH PRIVILEGES;
```

#### Update Configuration
Edit `src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dailyspend_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
    username: dailyspend_user
    password: password
```

#### Run Application
```bash
# Using Maven
mvn spring-boot:run

# Or build and run JAR
mvn clean package
java -jar target/dailyspend-backend-1.0.0.jar
```

### 2. Docker Compose (Recommended)

#### Start Services
```bash
# Build and start both MySQL and application
docker-compose up --build

# Or run in background
docker-compose up -d --build
```

#### Stop Services
```bash
docker-compose down

# To remove volumes as well
docker-compose down -v
```

### 3. Docker Only (Application)

#### Build Image
```bash
docker build -t dailyspend-backend .
```

#### Run Container
```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/dailyspend_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true" \
  -e SPRING_DATASOURCE_USERNAME="dailyspend_user" \
  -e SPRING_DATASOURCE_PASSWORD="password" \
  dailyspend-backend
```

## Testing the Application

### Health Check
```bash
curl http://localhost:8080/api/v1/accounts/health
```

### Run Test Script
```bash
# Make sure application is running first
./test-api.sh
```

### Manual API Testing

#### Get Membership Plans
```bash
curl -X GET http://localhost:8080/api/v1/membership-plans/active
```

#### Create Account
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

#### Get Account
```bash
curl -X GET http://localhost:8080/api/v1/accounts/1
```

## API Endpoints

### Account Management
- `POST /api/v1/accounts` - Create account
- `GET /api/v1/accounts/{id}` - Get account by ID
- `GET /api/v1/accounts/email/{email}` - Get account by email
- `GET /api/v1/accounts/mobile/{mobile}` - Get account by mobile
- `PUT /api/v1/accounts/{id}` - Update account
- `DELETE /api/v1/accounts/{id}` - Delete account
- `GET /api/v1/accounts` - Get all accounts

### Membership Plans
- `GET /api/v1/membership-plans` - Get all plans
- `GET /api/v1/membership-plans/active` - Get active plans
- `GET /api/v1/membership-plans/{id}` - Get plan by ID
- `GET /api/v1/membership-plans/type/{type}` - Get plans by type

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | Database URL | `jdbc:mysql://localhost:3306/dailyspend_db` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `password` |
| `SERVER_PORT` | Application port | `8080` |

## Profiles

### Development Profile
```yaml
spring:
  profiles:
    active: dev
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
```

### Production Profile
```yaml
spring:
  profiles:
    active: prod
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: validate
```

## Monitoring

### Application Logs
```bash
# Docker Compose
docker-compose logs -f app

# Docker
docker logs -f dailyspend-backend

# Local
tail -f logs/application.log
```

### Database Access
```bash
# Docker Compose MySQL
docker-compose exec mysql mysql -u dailyspend_user -p dailyspend_db

# Local MySQL
mysql -u dailyspend_user -p dailyspend_db
```

## Troubleshooting

### Common Issues

1. **Port 8080 already in use**
   ```bash
   # Change port in application.yml or use environment variable
   SERVER_PORT=8081 mvn spring-boot:run
   ```

2. **MySQL connection refused**
   - Ensure MySQL is running
   - Check connection parameters
   - Verify user permissions

3. **Build failures**
   ```bash
   # Clean and rebuild
   mvn clean compile
   ```

4. **Docker issues**
   ```bash
   # Clean Docker resources
   docker system prune -f
   ```

### Logs Location
- Application logs: `logs/application.log`
- Docker logs: `docker-compose logs`
- MySQL logs: `docker-compose logs mysql`

## Performance Tuning

### JVM Options
```bash
java -Xms512m -Xmx1024m -jar target/dailyspend-backend-1.0.0.jar
```

### Database Connection Pool
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
```

## Security Considerations

1. Change default passwords in production
2. Use environment variables for sensitive data
3. Enable HTTPS in production
4. Configure proper CORS settings
5. Implement authentication/authorization

## Next Steps

1. Add transaction management endpoints
2. Implement user authentication
3. Add API documentation (Swagger)
4. Set up monitoring and alerting
5. Configure CI/CD pipeline