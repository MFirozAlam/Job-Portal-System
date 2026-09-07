# Job Portal Frontend

Frontend for the Java Spring Boot Job Portal System.

## Stack

- React
- TypeScript
- Vite
- React Router
- Axios
- CSS

## Requirements

- Node.js 20.19+ recommended
- npm

## Run

```bash
npm install
npm run dev
```

Open:

http://localhost:5173

## Backend

The Spring Boot backend is expected at:

http://localhost:8080

The API base URL is configured through:

```text
VITE_API_BASE_URL=http://localhost:8080/api
```

Step 1 only creates the frontend foundation. API calls, authentication,
role-based routes, jobs, applications, and dashboards will be added next.
