# TaskTracer

TaskTracer is a REST API application for managing tasks.

The application allows users to create, retrieve, update and delete tasks, filter tasks by status, sort tasks by creation date or title, and automatically save tasks to a `tasks.txt` file when the application is shut down.

## Technologies

- Java 17
- Spring Boot 3.5.5
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Swagger / OpenAPI
- Docker

## Requirements

Before running the application, make sure you have installed:

- JDK 17 or higher
- Maven
- Docker

## Features

- Create a task
- Get all tasks
- Get a task by ID
- Update a task
- Delete a task
- Filter tasks by status
- Sort tasks by creation date
- Sort tasks by title
- Automatically save tasks to `tasks.txt` when the application is shut down
- Store tasks in PostgreSQL
- REST API
- Swagger / OpenAPI documentation

## Database Setup

The application uses PostgreSQL as the main data storage.

PostgreSQL can be started using Docker:

```bash
docker run --name task-tracker-db \
  -e POSTGRES_DB=task_tracker \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=momi \
  -p 5432:5432 \
  -d postgres:17
```

The application connects to PostgreSQL using the `DB_PASSWORD` environment variable:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/task_tracker
    username: postgres
    password: ${DB_PASSWORD}
```

Before starting the application, set the `DB_PASSWORD` environment variable to the PostgreSQL password.

For PowerShell:

```powershell
$env:DB_PASSWORD="momi"
```

### Create the tasks table

Run the following SQL command in the `task_tracker` database:

```sql
CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
```

## Run the Application

1. Start the PostgreSQL Docker container:

```bash
docker start task-tracker-db
```

2. Make sure PostgreSQL is running on port `5432`.

3. Set the database password.

For PowerShell:

```powershell
$env:DB_PASSWORD="momi"
```

4. Start the Spring Boot application:

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

## Swagger

Swagger UI is available at:

http://localhost:8080/swagger-ui/index.html

Swagger can be used to test all available REST API endpoints.

## REST API

### Get all tasks

```text
GET /tasks
```

### Get task by ID

```text
GET /tasks/{id}
```

### Create task

```text
POST /tasks
```

Example request:

```json
{
  "title": "Learn Spring Boot",
  "description": "Study REST API"
}
```

### Update task

```text
PUT /tasks/{id}
```

Example request:

```json
{
  "title": "Learn Spring Boot",
  "description": "Study JPA",
  "status": "IN_PROGRESS"
}
```

### Delete task

```text
DELETE /tasks/{id}
```

### Filter by status

```text
GET /tasks/status/{status}
```

Available statuses:

- `NEW`
- `IN_PROGRESS`
- `DONE`

Examples:

```text
GET /tasks/status/NEW
GET /tasks/status/DONE
```

### Sort by creation date

Oldest to newest:

```text
GET /tasks/sort/date/asc
```

Newest to oldest:

```text
GET /tasks/sort/date/desc
```

### Sort by title

Alphabetical order:

```text
GET /tasks/sort/title/asc
```

Reverse alphabetical order:

```text
GET /tasks/sort/title/desc
```

## File Export

When the application is shut down, all tasks stored in PostgreSQL are automatically exported to:

```text
tasks.txt
```

The application does not load tasks from `tasks.txt` on startup. PostgreSQL remains the main data storage.

The file is used as an additional export of the current tasks.