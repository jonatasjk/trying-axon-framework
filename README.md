# KYC Application with Axon Framework

This is a draft of KYC (Know Your Customer) application built using:
- Axon Framework
- CQRS
- Event Sourcing
- Saga patterns
- Quarqus
- MongoDB
- Kafka
- Auth0

## Prerequisites

- Java 21 or higher
- Docker and Docker Compose
- Maven

## Setup Instructions

### 1. Start Infrastructure Services

Start Axon Server and MongoDB using Docker Compose:

```bash
docker-compose up -d
```

This will start:
- **Axon Server** on ports 8024 (HTTP), 8124 (gRPC), 8224 (internal)
- **MongoDB** on port 27017

### 2. Access Axon Server Dashboard

Open your browser and navigate to:
```
http://localhost:8024
```

### 3. Build the Application

```bash
./mvnw clean install
```

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on port **8080**.

## Configuration

### application.yml

```yaml
spring:
  application:
    name: kyc
  data:
    mongodb:
      uri: mongodb://localhost:27017/kyc_db

server:
  port: 8080

axon:
  serializer:
    general: jackson
    events: jackson
    messages: jackson
  axonserver:
    servers: localhost:8124
    enabled: true
  eventhandling:
    processors:
      kyc-processor:
        mode: tracking
```

## Troubleshooting

### Cannot connect to Axon Server

Make sure Axon Server is running:
```bash
docker-compose ps
```

If not running, start it:
```bash
docker-compose up -d
```

### MongoDB connection issues

Check MongoDB is running:
```bash
docker exec -it kyc-mongodb mongosh
```

### View Axon Server Events

1. Open http://localhost:8024
2. Navigate to "Search" tab
3. View all events for your aggregate


## Decisions reasoning

### ID as a String instead of UUID
If used UUID:
- Jackson serializes it as a string anyway
- But Java expects it as a UUID on deserialization
- Any mismatch = deserialization failure

With String:
- JSON -> String (always safe)
- Kafka -> String
- Mongo -> String
- No custom serializers needed

### Why KycCreatedEvent is a record?
Because an event describes something that already happened:
- It must never change 
- It has no behavior 
- It is append-only


## License

This project is for training purposes. Written by Jônatas Kirsch.

