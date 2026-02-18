# Container Registry Tracker API
I created this repository which contains a Spring Boot REST API for managing Docker container images and their security scan details for my Uni assignment.

The microservice API integrates with PostgreSQL microservice for database modelling.

`Note: This application uses mock data only`

## Overview
- **Spring Boot REST API** - The application service for image and scan management
- **PostgreSQL 15** - The database storage

## Requirements per Assingment
- **Entity Relationship**: One-to-many (DockerImage → SecurityScans)
- **REST API**: CRUD operations for images and scans
- **Date Handling**: ISO 8601 format with date range filtering
- **Pagination**: Pagination for listing images
- **Deployment**: Simple deployment with Docker Compose, for easy demonstration

## Architecture

### Domain Model

- **DockerImage** (Parent)
  - Fields: id, imageName, tag, registry, internalNotes (Not Exposed in the API response) createdDate
  - One DockerImage may have multiple SecurityScans
  
- **SecurityScan** (Child)
  - Fields: id, imageId, scanDate, criticalVulns, highVulns, mediumVulns, lowVulns, scannerVersion (Not Exposed in the API Response) status
  - Many SecurityScans belong to one DockerImage

## API Endpoints

### Image Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/images` | Get paginated list of images |
| GET | `/api/v1/images/{id}` | Get specific image |
| POST | `/api/v1/images` | Create new image |
| PUT | `/api/v1/images/{id}` | Update image |
| DELETE | `/api/v1/images/{id}` | Delete image (cascades to scans) |

### Scan Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/images/{imageId}/scans` | Get all scans for an image |
| POST | `/api/v1/images/{imageId}/scans` | Create scan for an image |
| GET | `/api/v1/scans` | Get all scans with optional date filtering |
| GET | `/api/v1/scans/{id}` | Get specific scan |
| DELETE | `/api/v1/scans/{id}` | Delete scan |

## Example Usage

### Create an Image
```bash
curl -X POST http://localhost:8080/api/v1/images \
  -H "Content-Type: application/json" \
  -d '{
    "imageName": "nginx",
    "tag": "1.21.0",
    "registry": "docker.io",
    "createdDate": "2026-02-13"
  }'
```

### Get All Images
```bash
curl "http://localhost:8080/api/v1/images?page=0&size=10"
```

### Create a Security Scans (One to many relationship)
```bash
curl -X POST http://localhost:8080/api/v1/images/1/scans \
  -H "Content-Type: application/json" \
  -d '{
    "scanDate": "2026-02-13",
    "criticalVulns": 2,
    "highVulns": 5,
    "mediumVulns": 10,
    "lowVulns": 15,
    "status": "COMPLETED"
  }'
```

```bash
curl -X POST http://localhost:8080/api/v1/images/1/scans \
  -H "Content-Type: application/json" \
  -d '{
    "scanDate": "2026-02-14",
    "criticalVulns": 1,
    "highVulns": 2,
    "mediumVulns": 3,
    "lowVulns": 15,
    "status": "COMPLETED"
  }'
```

### Get Scans with Date Filter
```bash
curl "http://localhost:8080/api/v1/scans?fromDate=2026-02-13&toDate=2026-02-28"
```

### Get All Scans of Specific Image ID
```bash
curl "http://localhost:8080/api/v1/images/1/scans
```

### Delete image and it's Scans (Cascade Delete)
```bash
curl -X DELETE http://localhost:8080/api/v1/images/1
```

## Error Handling

One thing I learned while building this API is how important proper error handling is. I implemented a global exception handler using `@ControllerAdvice` (which I discovered is a really elegant pattern in Spring) that catches different types of errors and returns consistent JSON responses.

### Error Response Format

All errors return a standardized JSON structure:

```json
{
  "timestamp": "2026-02-13T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Docker image with id 999 not found",
  "path": "/api/v1/images/999"
}
```

### HTTP Status Codes Implemented

| Status Code | When it happens | Example |
|-------------|-----------------|---------|
| **400 Bad Request** | Invalid JSON or wrong data types | Sending text instead of a number for vulnerabilities |
| **404 Not Found** | Resource doesn't exist or endpoint is wrong | Trying to get image with ID that doesn't exist |
| **405 Method Not Allowed** | Using wrong HTTP method | Using POST on a GET-only endpoint |
| **415 Unsupported Media Type** | Missing or wrong Content-Type header | Forgetting `Content-Type: application/json` |
| **500 Internal Server Error** | Unexpected server issues | Database connection failure |

### Testing Error Responses

I tested these manually to make sure they work:

```bash
# Test 404 - Resource not found
curl http://localhost:8080/api/v1/images/9999

# Test 404 - Endpoint doesn't exist
curl http://localhost:8080/api/v1/wrongendpoint

# Test 405 - Method not allowed
curl -X POST http://localhost:8080/api/v1/images/1

# Test 400 - Invalid JSON
curl -X POST http://localhost:8080/api/v1/images \
  -H "Content-Type: application/json" \
  -d '{"imageName": "test", "createdDate": "not-a-date"}'
```


## Project Structure
```
imageTracker/
├── src/main/java/com/registrytracker/
│   ├── RegistryTrackerApplication.java    # Main Spring Boot application
│   ├── controller/                         # REST endpoints
│   │   ├── ImageController.java
│   │   └── ScanController.java
│   ├── dto/                                # Data transfer objects
│   │   ├── ErrorResponse.java
│   │   ├── ImageDTO.java
│   │   └── ScanDTO.java
│   ├── entity/                             # JPA entities (database tables)
│   │   ├── DockerImage.java
│   │   └── SecurityScan.java
│   ├── exception/                          # Custom exceptions and error handling
│   │   ├── GlobalExceptionHandler.java
│   │   └── ResourceNotFoundException.java
│   └── repository/                         # Data access layer
│       ├── ImageRepository.java
│       └── ScanRepository.java
├── src/main/resources/
│   └── application.properties             # Database and app configuration
├── pom.xml                                # Maven dependencies
├── Dockerfile                             # Container image definition
└── docker-compose.yml                     # Multi-container setup
```

## Running the Application

I set up Docker Compose to make it easier to run both the database and application together:

```bash
docker compose up -d --build
```

The API will be available at `http://localhost:8080`. I configured PostgreSQL to run on port 5432.