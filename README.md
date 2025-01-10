# Spring Boot Demo Application

## Description
This is a Spring Boot web application that allows for the management of Teams and Players. The application provides the following features:

1. **Listing Teams and Players** in a web page.
2. **Creating, Updating, and Deleting Teams and Players** via a REST API.
3. **Storing data** in a PostgreSQL database using JPA and Hibernate.
4. **Reproducible builds** using Maven.
5. **Dockerized environment** for seamless deployment.

---

## Features

- **REST API Endpoints**:
  - Create, retrieve, update, and delete Teams.
  - Create, retrieve, update, and delete Players.
- **Database Persistence**:
  - Stores data in a PostgreSQL relational database.
- **User Interface**:
  - Simple HTML pages to display and manage Teams and Players.
- **Containerization**:
  - Fully containerized using Docker and Docker Compose.

---

## Prerequisites

- Docker and Docker Compose installed.
- Java 17 installed locally (if building outside Docker).
- Maven installed (if building outside Docker).

---

## Quick Start

### Clone the Repository
```bash
git clone <repository-url>
cd demo
```

### Run the Application with Docker Compose
```bash
docker-compose up --build
```

This will:
- Build the Spring Boot application into a Docker image.
- Start the application container and a PostgreSQL database container.

You can also use for the app and the db :

```bash
docker-compose up --build app db
```

And for the tests :

```bash
docker-compose up --build tests
```
And the check the result in ./target/surefire-reports

Warning : Docker and hibernate doesn't function well together and you may need to run instead :

```bash
mvn test
```

### Access the Application

- API Endpoints: `http://localhost:9090/api/`
- Web Interface: `http://localhost:9090`

---

## REST API Endpoints

### Teams

#### Get All Teams
```bash
GET /api/teams
```
**Response:**
```json
[
  { "id": 1, "name": "Team A" },
  { "id": 2, "name": "Team B" }
]
```

#### Create a New Team
```bash
POST /api/teams
Content-Type: application/json
{
  "name": "Team C"
}
```

#### Update an Existing Team
```bash
PUT /api/teams/{id}
Content-Type: application/json
{
  "name": "Updated Team Name"
}
```

#### Delete a Team
```bash
DELETE /api/teams/{id}
```

### Players

#### Get All Players
```bash
GET /api/players
```
**Response:**
```json
[
  { "id": 1, "name": "Player 1", "team": { "id": 1, "name": "Team A" } },
  { "id": 2, "name": "Player 2", "team": { "id": 2, "name": "Team B" } }
]
```

#### Create a New Player
```bash
POST /api/players
Content-Type: application/json
{
  "name": "Player Name",
  "team": { "id": 1 }
}
```

#### Update an Existing Player
```bash
PUT /api/players/{id}
Content-Type: application/json
{
  "name": "Updated Player Name",
  "team": { "id": 2 }
}
```

#### Delete a Player
```bash
DELETE /api/players/{id}
```

---

## Configuration

### Environment Variables

The following environment variables can be configured in `docker-compose.yml`:

| Variable                     | Description                               | Default            |
|------------------------------|-------------------------------------------|--------------------|
| `SPRING_DATASOURCE_URL`      | JDBC URL for PostgreSQL                  | `jdbc:postgresql://db:5432/teams` |
| `SPRING_DATASOURCE_USERNAME` | PostgreSQL username                      | `postgres`         |
| `SPRING_DATASOURCE_PASSWORD` | PostgreSQL password                      | `mdp`              |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | Hibernate DDL mode (e.g., `update`)    | `update`           |

### Ports

- Application: `http://localhost:9090`
- PostgreSQL: `localhost:5432`

---

## Database Setup

The application uses PostgreSQL to persist data. The database schema and tables are automatically created when the application starts, based on the JPA entities.

If you need to manually interact with the database:

1. Connect to the PostgreSQL container:
   ```bash
   docker exec -it postgres-db psql -U postgres -d teams
   ```
2. View tables:
   ```sql
   \dt
   ```
3. Query data:
   ```sql
   SELECT * FROM team;
   SELECT * FROM player;
   ```

---

## Running Tests

### Unit Tests

To run the unit tests, use Maven:
```bash
mvn test
```
The tests cover:
- Team and Player repository operations.
- REST API endpoints.

---

## Project Structure

```
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.demo
│   │   │       ├── Controllers
│   │   │       ├── Entities
│   │   │       ├── Repositories
│   │   │       └── DemoApplication.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── templates
│   │           ├── index.html
│   │           └── error.html
│   └── test
│       └── java
│           └── com.example.demo
│               ├── TeamControllerTest.java
│               ├── PlayerControllerTest.java
│               └── RepositoryTests.java
├── Dockerfile
├── docker-compose.yml
└── README.md
```
