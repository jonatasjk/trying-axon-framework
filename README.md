# KYC Application with Axon Framework

This is a draft of KYC (Know Your Customer) application built using:
- Axon Framework
- CQRS
- Event Sourcing
- Saga patterns
- MongoDB
- Kafka

## Prerequisites
- Java 23 or higher
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

### 3. Make sure Kafka is running
Configure the url and port in the application.yml if needed.

### 4. Build the Application

```bash
./mvnw clean install
```

### 5. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on port **8080**.

## Configuration

### application.yml
Configure Axon Server, MongoDB, and Kafka settings in `src/main/resources/application.yml` if needed.


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

### Security
Skipped security for now so it's quick to run and test this sample repo.

### ExternalKycClient
This is a mock client simulating an external KYC provider.
In a real-world application, this would be replaced with actual API calls to a third-party KYC service.

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

### Events are records - not classes
Because an event describes something that already happened:
- It must never change 
- It has no behavior 
- It is append-only

### KycAggregate
KycAggregate applies the KycCreatedEvent and sets the status to PENDING.
- Command side: Represents the write model.
- Purpose: Handles commands (e.g., CreateKycCommand) and enforces business rules.
- Event Sourcing: Stores state changes as events (e.g., KycCreatedEvent) instead of persisting the current state.
- Lifecycle: Rehydrates its state by replaying events from the event store.

### KycProjection
KycProjection saves a new KycView to MongoDB when a KycCreatedEvent is handled.
- Query side: Represents the read model.
- Purpose: Listens to events (e.g., KycCreatedEvent) and updates a materialized view (KycView) for fast querying.
- Persistence: Stores the current state in a database (e.g., MongoDB) for efficient reads.


## License
This project is for training purposes. Written by Jônatas Kirsch.

