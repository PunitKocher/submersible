# Submersible Probe REST API

This is a Spring Boot REST API that simulates the control of a remotely operated submersible probe navigating a 2D ocean grid. The project is developed as part of a scenario-based coding test.

---

## Features

- Grid-based ocean floor (2D coordinate system)
- Movable probe with orientation (`NORTH`, `EAST`, `SOUTH`, `WEST`)
- Supports command input (`F`, `B`, `L`, `R`)
- Obstacle avoidance logic
- Grid boundary enforcement
- Tracks and returns all visited coordinates
- Fully tested using JUnit 5 and MockMvc
- Clean code using Java 17 features (e.g. records, switch expressions)
- Input validation using `@Valid` and Spring Boot validation

---

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Validation (`jakarta.validation`)
- JUnit 5
- MockMvc
- Jackson for JSON serialization
- Lombok (used partially)

---

## API Usage

### `POST /api/probe/execute`

Send commands to move the probe on the grid.

### 🧾 Request Example

```json
{
  "start": { "x": 0, "y": 0 },
  "direction": "NORTH",
  "gridWidth": 5,
  "gridHeight": 5,
  "obstacles": [ { "x": 2, "y": 2 } ],
  "commands": "FFRFF"
}
```  

### Response Example

```json
{
  "current": { "x": 2, "y": 1 },
  "direction": "SOUTH",
  "visited": [
    { "x": 0, "y": 0 },
    { "x": 0, "y": 1 },
    { "x": 0, "y": 2 },
    { "x": 1, "y": 2 },
    { "x": 2, "y": 2 }
  ]
}
```