# Supermarket Management System

A comprehensive monolithic backend application built with **Java 17+**, **Spring Boot**, and **Apache Kafka** for managing supermarket operations with role-based access control, real-time inventory management, and automated reporting.

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based authentication with access and refresh tokens
- Role-based access control (ADMIN, USER)
- Spring Security integration

### 📦 Core Domain
- **Products**: Full CRUD operations with stock management, discounts, and low-stock alerts
- **Categories**: Product categorization with hierarchical support
- **Shopping Cart**: User-specific cart management with real-time totals
- **Orders**: Order processing with status tracking and payment simulation
- **Users**: User management with profile and order history

### 🔧 Admin Capabilities
- Complete product and category management
- Dynamic discount management
- Stock level monitoring with low-stock alerts
- Comprehensive sales analytics (daily, weekly, monthly, quarterly, yearly)
- Sales reports by category and product
- Customer behavior analytics
- PDF report generation using iText

### 🛍️ Customer Capabilities
- Product browsing and search with filtering
- Shopping cart management
- Order placement with payment simulation
- Order history and receipt download
- Profile management

### 📊 Kafka Integration
Asynchronous event processing for:
- **Order Placed Events**: Triggers inventory updates and email notifications
- **Inventory Update Events**: Stock level changes and reorder alerts
- **Daily Summary Events**: Automated report generation
- **Stock Low Events**: Real-time stock alerts
- **Payment Confirmed Events**: Payment processing notifications

### 🏗️ Technical Features
- **Caching**: Spring Cache with Caffeine for improved performance
- **Database**: PostgreSQL with optimized indexing
- **API Documentation**: OpenAPI 3.0 (Swagger UI)
- **Validation**: Bean validation with custom constraints
- **Pagination**: Consistent pagination across all endpoints
- **Error Handling**: Global exception handling with detailed error responses
- **Health Monitoring**: Spring Boot Actuator endpoints

## 🛠️ Technology Stack

- **Java 17+**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA** (Hibernate)
- **Apache Kafka** for event streaming
- **PostgreSQL** database
- **Spring Cache** with Caffeine
- **iText** for PDF generation
- **Docker & Docker Compose**
- **Maven** for dependency management
- **OpenAPI 3.0** for API documentation

## 📋 Prerequisites

- **Java 17** or higher
- **Docker** and **Docker Compose**
- **Git**

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone <repository-url>
cd supermarket-management
```

### 2. Start the Application with Docker
```bash
# Start all services (PostgreSQL, Kafka, Application)
docker-compose up -d

# View logs
docker-compose logs -f supermarket-app
```

### 3. Access the Application
- **API Base URL**: http://localhost:8080/api/v1
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Kafka UI**: http://localhost:8082
- **Health Check**: http://localhost:8080/api/v1/actuator/health

### 4. Default Admin Account
After the application starts, create an admin account through registration and manually update the role in the database:

```sql
-- Connect to the database
docker exec -it supermarket-postgres psql -U supermarket_user -d supermarket_db

-- Update user role to ADMIN
UPDATE users SET role = 'ADMIN' WHERE username = 'your_admin_username';
```

## 🏃‍♂️ Development Setup

### Local Development (without Docker)

1. **Start Infrastructure Services**
```bash
# Start only PostgreSQL and Kafka
docker-compose up -d postgres kafka zookeeper
```

2. **Run the Application**
```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or build and run
./mvnw clean package
java -jar target/supermarket-management-1.0.0.jar
```

3. **Run Tests**
```bash
./mvnw test
```

## 📚 API Documentation

### Authentication Endpoints

#### Register User
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890"
}
```

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```

#### Refresh Token
```http
POST /api/v1/auth/refresh
Content-Type: application/json

{
  "refreshToken": "your_refresh_token_here"
}
```

### Product Endpoints

#### Get All Products
```http
GET /api/v1/products?page=0&size=20
Authorization: Bearer <access_token>
```

#### Search Products
```http
GET /api/v1/products/search?q=apple&page=0&size=20
Authorization: Bearer <access_token>
```

#### Filter Products
```http
GET /api/v1/products/filter?search=apple&categoryId=1&minPrice=10&maxPrice=100
Authorization: Bearer <access_token>
```

#### Create Product (Admin Only)
```http
POST /api/v1/products
Authorization: Bearer <admin_access_token>
Content-Type: application/json

{
  "name": "Apple iPhone 15",
  "description": "Latest iPhone model",
  "price": 999.99,
  "stock": 50,
  "discount": 5.0,
  "lowStockThreshold": 10,
  "category": {
    "id": 1
  }
}
```

### Category Endpoints

#### Get All Categories
```http
GET /api/v1/categories
Authorization: Bearer <access_token>
```

#### Create Category (Admin Only)
```http
POST /api/v1/categories
Authorization: Bearer <admin_access_token>
Content-Type: application/json

{
  "name": "Electronics",
  "description": "Electronic devices and accessories"
}
```

## 🐳 Docker Configuration

### Services Overview

| Service | Port | Description |
|---------|------|-------------|
| PostgreSQL | 5432 | Primary database |
| Kafka | 9092 | Message broker |
| Zookeeper | 2181 | Kafka coordination |
| Kafka UI | 8082 | Kafka monitoring |
| Application | 8080 | Main API |

### Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_PROFILES_ACTIVE` | `dev` | Active profile |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/supermarket_db` | Database URL |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | `localhost:9092` | Kafka servers |
| `JWT_SECRET` | `mySecretKey...` | JWT signing secret |

## 📊 Kafka Topics

| Topic | Purpose | Partitions |
|-------|---------|------------|
| `order.placed` | Order creation events | 3 |
| `inventory.update` | Stock level changes | 3 |
| `daily.summary` | Daily report triggers | 1 |
| `stock.low` | Low stock alerts | 1 |
| `payment.confirmed` | Payment notifications | 3 |

## 🗄️ Database Schema

### Core Tables
- `users` - User accounts and authentication
- `categories` - Product categories
- `products` - Product catalog
- `carts` - Shopping carts
- `cart_items` - Cart line items
- `orders` - Order headers
- `order_items` - Order line items

### Key Relationships
- Users have one Cart
- Cart contains many CartItems
- Users have many Orders
- Orders contain many OrderItems
- Products belong to Categories
- Products can be in multiple Carts and Orders

## 🔍 Monitoring & Health Checks

### Application Health
```http
GET /api/v1/actuator/health
```

### Application Metrics
```http
GET /api/v1/actuator/metrics
```

### Kafka Monitoring
Access Kafka UI at http://localhost:8082 to monitor:
- Topic messages
- Consumer groups
- Broker status

## 🛡️ Security Features

- **JWT Authentication**: Stateless token-based authentication
- **Role-based Authorization**: ADMIN and USER roles
- **Password Encryption**: BCrypt hashing
- **CORS Configuration**: Configurable cross-origin requests
- **Input Validation**: Bean validation on all inputs
- **SQL Injection Protection**: JPA parameterized queries

## 📈 Performance Optimizations

- **Caching**: Product and category caching with Caffeine
- **Database Indexing**: Optimized indexes on searchable fields
- **JPA Optimizations**: N+1 query prevention with @EntityGraph
- **Connection Pooling**: HikariCP for database connections
- **Async Processing**: Kafka for non-blocking operations

## 🧪 Testing

### Run All Tests
```bash
./mvnw test
```

### Test Categories
- **Unit Tests**: Service layer testing
- **Integration Tests**: Repository and API testing
- **Security Tests**: Authentication and authorization
- **Kafka Tests**: Event processing validation

## 🚀 Production Deployment

### Environment-specific Profiles
```yaml
# application-prod.yml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS}
```

### Build for Production
```bash
./mvnw clean package -Pprod
```

### Health Check Configuration
```bash
# Docker health check
curl -f http://localhost:8080/api/v1/actuator/health || exit 1
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Troubleshooting

### Common Issues

1. **Port Conflicts**
   ```bash
   # Check if ports are in use
   netstat -tulpn | grep -E ':(5432|9092|8080|2181)'
   ```

2. **Java Version**
   ```bash
   # Verify Java 17+
   java -version
   ```

3. **Docker Issues**
   ```bash
   # Reset Docker environment
   docker-compose down -v
   docker-compose up -d
   ```

4. **Database Connection**
   ```bash
   # Check PostgreSQL container
   docker logs supermarket-postgres
   ```

5. **Kafka Connection**
   ```bash
   # Check Kafka container
   docker logs supermarket-kafka
   ```

### Logs and Debugging
```bash
# Application logs
docker-compose logs -f supermarket-app

# All service logs
docker-compose logs -f

# Specific service logs
docker-compose logs -f postgres
docker-compose logs -f kafka
```

## 📞 Support

For support and questions:
- Create an issue in the repository
- Check the [troubleshooting section](#-troubleshooting)
- Review the [API documentation](#-api-documentation)

---

**Built with ❤️ using Spring Boot and Apache Kafka**