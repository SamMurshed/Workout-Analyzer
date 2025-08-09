# Workout Analyzer

**One-line:** A **Java Spring Boot** REST service that ingests workout logs, persists them in an embedded **H2** DB, and exposes analytics endpoints for per-entry volume, per-exercise summary, and weekly trends.

---

## Badges
**Status:** `WIP` • **Tech:** `Java`, `Spring Boot`, `H2` • **ML (planned):** `TensorFlow (Python)`

---

## Tech stack
- **Language:** `Java 17+`
- **Framework:** `Spring Boot` (Spring MVC, Spring Data JPA)
- **DB:** `H2` (embedded)
- **Build:** `Maven`
- **JSON:** `Jackson`
- **Local tests:** VS Code REST Client (`.http`) / `curl`

---

## Quickstart (local)
1. **Prereqs:** Java 17+ installed.
2. **Run:** (from project root)
```bash
mvn spring-boot:run
```
3. **Health:**
```
GET http://localhost:8080/
# returns: "Workout Analyzer is up and running!"
```

---

## API — key endpoints (compact)
- **Create workout**  
  `POST /api/workouts`  
  Body JSON: `{ "exercise","sets","reps","weight","date" }`  
  Example:
```bash
curl -X POST http://localhost:8080/api/workouts   -H "Content-Type: application/json"   -d '{"exercise":"Squat","sets":4,"reps":8,"weight":135,"date":"2025-07-27"}'
```

- **List workouts**  
  `GET /api/workouts`

- **Per-entry volume**  
  `GET /api/workouts/analysis/volume`  
  Response: `[{ id, exercise, date, volume }]`

- **Per-exercise summary** (date-range optional)  
  `GET /api/workouts/analysis/summary?start=YYYY-MM-DD&end=YYYY-MM-DD`  
  Response: `[{ exercise, totalVolume }]`

- **Weekly trend** (ISO week)  
  `GET /api/workouts/analysis/trend?start=…&end=…`  
  Response: `[{ weekStart, totalVolume }]`

---

## Data model (high level)
- **Entity:** `WorkoutEntry { id: Long, exercise: String, sets: int, reps: int, weight: double, date: LocalDate }`
- **DTOs:** `WorkoutVolumeDto`, `WorkoutSummaryDto`, `WorkoutTrendDto` (computed/aggregated payloads)

---

## ML integration (planned)
- **Approach:** Python microservice (FastAPI/Flask) running a **TensorFlow SavedModel**.
- **Flow:** Java service posts aggregated features → Python ML returns recommendations (JSON).
- **Reason:** leverage Python ML ecosystem for model development while keeping Java for production ingestion.

---

## Deployment
- **Targets:** Railway / Render / Heroku (free tiers).
- **CI/CD:** connect repo to host for automatic deploy on push.

---

## Testing & validation
- **Integration:** .http files or curl for quick testing.
- **Planned:** add validation annotations (`@NotBlank`, `@Min`) and `@ControllerAdvice` for standardized errors.
- **Planned tests:** JUnit + Spring Boot Test

---

## Contribute: fork → feature branch → PR.
