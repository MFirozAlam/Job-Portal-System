# Job Portal System - Complete Backend

Spring Boot + MySQL REST API implementing authentication, employer jobs, candidate applications, application status and optional admin management.

## Requirements
- Java 17+
- Maven 3.9+
- MySQL 8+

## Database
The application connects to:
`jdbc:mysql://localhost:3306/job_portal`
and creates/updates tables automatically with `spring.jpa.hibernate.ddl-auto=update`.

Change `src/main/resources/application.properties` if your MySQL username/password differs.

## Run
```bash
mvn clean spring-boot:run
```
API: `http://localhost:8080`

## Authentication
Login creates an HTTP session. For a browser/frontend, send requests with credentials enabled (`withCredentials: true` in Axios).

Default admin:
- email: `admin@jobportal.com`
- password: `Admin@123`

Register candidates/employers through `/api/auth/register`.

## Main endpoints

### Auth
- POST `/api/auth/register`
- POST `/api/auth/login`
- POST `/api/auth/logout`
- GET `/api/auth/me`

### Jobs
- GET `/api/jobs`
- GET `/api/jobs/search?keyword=java&location=hyderabad&jobType=FULL_TIME&minSalary=30000&maxSalary=100000`
- GET `/api/jobs/{id}`
- GET `/api/jobs/mine` (employer)
- POST `/api/jobs` (employer)
- PUT `/api/jobs/{id}` (owner employer)
- DELETE `/api/jobs/{id}` (owner employer)

### Applications
- POST `/api/applications/jobs/{jobId}` (candidate)
- GET `/api/applications/mine` (candidate)
- GET `/api/applications/jobs/{jobId}` (job owner employer)
- PUT `/api/applications/{applicationId}/status` (job owner employer)

### Admin
- GET `/api/admin/users`
- DELETE `/api/admin/users/{id}`
- GET `/api/admin/jobs`
- DELETE `/api/admin/jobs/{id}`
- GET `/api/admin/applications`

## Example registration
```json
{
  "name": "Firoz",
  "email": "firoz@example.com",
  "password": "secret123",
  "role": "CANDIDATE"
}
```

## Example employer job
```json
{
  "title": "Java Developer",
  "companyName": "ABC Technologies",
  "location": "Hyderabad",
  "salary": 70000,
  "jobType": "FULL_TIME",
  "description": "Develop Spring Boot applications.",
  "requiredSkills": "Java, Spring Boot, MySQL, REST API"
}
```

## Example status update
```json
{"status":"SHORTLISTED"}
```

Note: session-based authentication is used instead of JWT so the backend remains simple and suitable for the assignment. Role/ownership checks are enforced in the service/controller layer.
