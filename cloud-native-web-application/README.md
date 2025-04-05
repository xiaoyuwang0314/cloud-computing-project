# Movie Web Application

## Project Overview
A cloud-native web application for movie management built with Spring Boot and MySQL. The application features secure user authentication, movie data management, and health monitoring capabilities.

## Technical Stack
- **Language**: Java (OpenJDK 17)
- **Framework**: Spring Boot 3.4.1
- **Database**: MySQL 8.0
- **Authentication**: JWT (JSON Web Token)
- **Web Server**: Nginx
- **Cloud Platform**: AWS
- **Infrastructure**: EC2 (t2.micro), Ubuntu 24.04
- **Infrastructure as Code**: Packer

## Features
- User registration and authentication with JWT
- RESTful API endpoints for movie management
- Database health monitoring
- Secure password encryption with BCrypt
- Nginx reverse proxy configuration
- Stateless session management
- No-cache headers for security

## Prerequisites
- Java 17 or higher
- Maven 3.x
- MySQL 8.0
- AWS CLI configured
- SSH client
- Packer

## Project Structure
```
movie/
├── src/main/java/com/xiaoyu/movie/
│   ├── config/          # Application configurations
│   ├── controller/      # REST controllers
│   ├── security/        # Security configurations
│   └── util/           # Utility classes
├── src/main/resources/
│   └── application.properties  # Application settings
└── pom.xml             # Project dependencies
```

## Environment Variables
The application requires the following environment variables:
- `DB_URL`: MySQL database URL
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `WEBAPP_SECRET_KEY`: JWT secret key

## Build and Deploy Instructions

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd movie
   ```

2. **Build the Project**
   ```bash
   mvn clean install
   ```

3. **Configure AWS Environment**
   - Set up AWS CLI with credentials
   - Configure the following AWS resources:
     - VPC with public and private subnets
     - Security groups for web and database servers
     - EC2 instances for application and database

4. **Build AMI with Packer**
   ```bash
   # Copy JAR to packer directory
   cp ./movie/target/movie-0.0.1-SNAPSHOT.jar ./packer/movie.jar
   
   # Initialize and validate Packer template
   cd packer
   packer init .
   packer validate .
   
   # Build AMI
   packer build .
   ```
   The Packer configuration:
   - Uses Ubuntu 24.04 as base AMI
   - Installs OpenJDK 17
   - Configures Nginx as reverse proxy
   - Sets up the application as a service
   - Creates a custom AMI with all dependencies

5. **Database Setup**
   - Ensure MySQL is installed and running
   - Create the required database schema
   - Configure database credentials in environment variables

6. **Application Deployment**
   - Transfer the JAR file to EC2 instance
   - Configure environment variables on EC2
   - Start the application with:
     ```bash
     nohup java -jar movie.jar > application.log 2>&1 &
     ```

7. **Nginx Configuration**
   - Install and configure Nginx
   - Set up reverse proxy to forward requests to the application
   - Enable and start Nginx service

## API Endpoints
- `POST /v1/register`: User registration
- `POST /v1/login`: User authentication
- `GET /v1/healthcheck`: Application health status

## Security Configurations
- JWT-based authentication
- Stateless session management
- BCrypt password encryption
- No-cache headers
- CORS configuration

## CI/CD Pipeline
The project includes GitHub Actions workflows for:
- Continuous Integration
- Automated Testing
- AWS Deployment
- Infrastructure Management

## AWS Infrastructure
- **VPC Configuration**
  - Public subnet for web application
  - Private subnet for database
  - Internet Gateway for public access
  - Route tables for network traffic

- **Security Groups**
  - Web application security group (port 80, 8080)
  - Database security group (port 3306)

## Secret Management
The following secrets are managed through GitHub Actions:
- `EC2_SSH_KEY`: SSH key for EC2 access
- `AWS_ACCESS_KEY_ID`: AWS access key
- `AWS_SECRET_ACCESS_KEY`: AWS secret key
- Database credentials
- JWT secret key

## Environment Names
- `AWS_DEPLOYMENT`: Production environment

## Monitoring and Logging
- Application logs in `/home/ubuntu/assign.log`
- Nginx access and error logs
- Database health monitoring through `/v1/healthcheck`

## Infrastructure as Code
### Packer Configuration
The project uses HashiCorp Packer to create custom AMIs with:
- Base OS: Ubuntu 24.04
- Pre-installed dependencies:
  - OpenJDK 17
  - Nginx
- Configured components:
  - Application JAR file
  - Nginx reverse proxy settings
  - System service configuration
- AMI Features:
  - Automated setup
  - Consistent environment
  - Ready for deployment
  - Reduced deployment time

### Packer Template Structure
```hcl
packer/
├── webapp.pkr.hcl         # Main Packer template
├── file/
│   └── nginx.conf        # Nginx configuration
└── movie.jar             # Application artifact
```
