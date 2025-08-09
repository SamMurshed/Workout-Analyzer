Workout Analyzer

Java • Spring Boot • H2 • REST API

A lightweight analytics service that ingests user workout logs, persists them to an embedded H2 database, and exposes analytical REST endpoints for per-entry volume, per-exercise summary, and weekly trend. The project is designed to be a clean Java backend suitable for adding a TensorFlow-powered Python microservice for ML-driven imbalance detection.

Quick overview

Purpose: Provide a simple, testable backend for storing workouts and computing training analytics (volume, summaries, trends).

Audience: Engineers, recruiters, or teammates who want to inspect or consume workout analytics via HTTP.

Features

POST /api/workouts — ingest workout logs (exercise, sets, reps, weight, date)

GET  /api/workouts — list all workouts

GET  /api/workouts/analysis/volume — per-entry computed volume (sets * reps * weight)

GET  /api/workouts/analysis/summary — aggregate total volume per exercise (supports ?start=YYYY-MM-DD&end=YYYY-MM-DD)

GET  /api/workouts/analysis/trend — weekly volume time-series (ISO week bucketing)

Simple DTOs to shape response payloads and keep the persistence model private

Tech stack

Language: Java

Framework: Spring Boot (Spring MVC, Spring Data JPA)

Database: H2 (embedded, in-memory for development)

Build: Maven

JSON: Jackson

Dev tools: VS Code, REST Client (.http files)

Planned ML integration: Python (FastAPI/Flask) + TensorFlow for recommendations

Getting started (local)

Prerequisites

Java 17+ installed and JAVA_HOME set

Maven (optional if you prefer using the IDE run button)

Run the app

From the project root (where pom.xml is):

# If you have Maven installed
mvn spring-boot:run

# Or, run from your IDE by launching the `main` method in WorkoutAnalyzerApplication

Once running, the service listens on http://localhost:8080.

Health check

GET http://localhost:8080/
# returns: "Workout Analyzer is up and running!"

API — examples

Below are sample curl snippets. You can also use the included .http files or Postman.

1) Create a workout

curl -X POST http://localhost:8080/api/workouts \
  -H "Content-Type: application/json" \
  -d '{ "exercise":"Squat","sets":4,"reps":8,"weight":135,"date":"2025-07-27" }'

Response: saved WorkoutEntry JSON with generated id.

2) List all workouts

curl http://localhost:8080/api/workouts

3) Per-entry volume

curl http://localhost:8080/api/workouts/analysis/volume

4) Per-exercise summary (with optional date range)

# All-time summary
curl http://localhost:8080/api/workouts/analysis/summary

# Filtered by date range
curl "http://localhost:8080/api/workouts/analysis/summary?start=2025-07-01&end=2025-07-31"

5) Weekly trend

curl http://localhost:8080/api/workouts/analysis/trend

Data model (high level)

WorkoutEntry (@Entity) — id: Long, exercise: String, sets: int, reps: int, weight: double, date: LocalDate.

DTOs — WorkoutVolumeDto, WorkoutSummaryDto, WorkoutTrendDto used to shape responses and include computed/aggregated fields.

H2 console (optional)

To inspect the in-memory DB, enable Spring Boot's H2 console in application.properties during development:

spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb

Then visit: http://localhost:8080/h2-console and use JDBC URL jdbc:h2:mem:testdb.

Testing

Use the provided .http files in the project root for quick integration tests:

WorkoutRequests.http (sample POSTs)

get-all.http (GET requests)

Manually verify JSON responses and DB persistence via H2 console.

ML microservice (integration plan)

Goal: add imbalance detection and exercise recommendations using TensorFlow.

Approach: build a small Python FastAPI microservice that accepts JSON summaries (volumes, trends), runs inference with a TensorFlow SavedModel, and returns ranked exercise recommendations. The Java service will call this microservice via HTTP when /analysis/imbalance is requested.

Why: decouples model training & experimentation (Python) from production ingestion and API code (Java).

Deployment

Recommended hosts: Railway or Render for free-tier deployments.

Deploy two services if using Python microservice: Java Spring Boot service + Python ML service. Both can be linked to GitHub for automated deploys.

Next steps & roadmap

Add input validation (@NotBlank, @Min) and global error handling (@ControllerAdvice).

Implement /analysis/imbalance that posts summaries to the ML microservice and returns recommendations.

Add unit & integration tests (JUnit + Spring Boot Test).

Containerize with Docker and deploy both services to Railway/Render.

Contributing

Contributions and improvements welcome. Fork the repository, make changes on a feature branch, and open a PR.

License

MIT License — feel free to reuse and adapt.

Contact

Author: [Your Name] — add your GitHub profile link or email here.
