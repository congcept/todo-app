# Todo App

## Tech Stack

- **Backend:** Java 21, Spring Boot 3.3, PostgreSQL 16, Flyway
- **Frontend:** React 19, TypeScript, Tailwind CSS, TanStack Query
- **Infra:** Docker, Docker Compose, Nginx

## How to run

```bash
docker compose up --build -d
```

Open http://localhost:3000

## API endpoints

- `GET /api/tasks` — list tasks (?status=PENDING&page=0&size=10&sort=createdAt,desc)
- `GET /api/tasks/{id}` — get one
- `POST /api/tasks` — create (`{"title":"...", "description":"..."}`)
- `PUT /api/tasks/{id}` — update
- `PATCH /api/tasks/{id}/status` — toggle PENDING/COMPLETED
- `DELETE /api/tasks/{id}` — delete
