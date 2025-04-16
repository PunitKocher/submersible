package com.maveric.submersible.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.submersible.dto.CommandRequest;
import com.maveric.submersible.model.Direction;
import com.maveric.submersible.model.Position;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProbeController.class)
class ProbeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnFinalProbeState() throws Exception {
        CommandRequest request = new CommandRequest(
                new Position(1, 1),
                Direction.NORTH,
                5,
                5,
                Set.of(new Position(3, 3)),
                "FFRFF"
        );

        mockMvc.perform(post("/api/probe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current.x").value(2))
                .andExpect(jsonPath("$.current.y").value(3))
                .andExpect(jsonPath("$.direction").value("EAST"));
    }

    @Test
    void shouldMoveCorrectlyWithoutObstacles() throws Exception {
        CommandRequest request = new CommandRequest(
                new Position(0, 0),
                Direction.EAST,
                5,
                5,
                Set.of(),
                "FFRFF"
        );

        mockMvc.perform(post("/api/probe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current.x").value(2))
                .andExpect(jsonPath("$.current.y").value(0))
                .andExpect(jsonPath("$.direction").value("SOUTH"));
    }

    @Test
    void shouldStopBeforeObstacle() throws Exception {
        CommandRequest request = new CommandRequest(
                new Position(0, 0),
                Direction.EAST,
                5,
                5,
                Set.of(new Position(2, 0)),
                "FFF"
        );

        mockMvc.perform(post("/api/probe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current.x").value(1))
                .andExpect(jsonPath("$.current.y").value(0));
    }

    @Test
    void shouldNotMoveOutOfGrid() throws Exception {
        CommandRequest request = new CommandRequest(
                new Position(0, 0),
                Direction.WEST,
                3,
                3,
                Set.of(),
                "F"
        );

        mockMvc.perform(post("/api/probe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current.x").value(0));
    }

    @Test
    void shouldOnlyRotateAndNotMove() throws Exception {
        CommandRequest request = new CommandRequest(
                new Position(2, 2),
                Direction.NORTH,
                5,
                5,
                Set.of(),
                "RRLL"
        );

        mockMvc.perform(post("/api/probe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current.x").value(2))
                .andExpect(jsonPath("$.current.y").value(2))
                .andExpect(jsonPath("$.direction").value("NORTH"));
    }

    @Test
    void shouldTrackVisitedPositions() throws Exception {
        CommandRequest request = new CommandRequest(
                new Position(0, 0),
                Direction.NORTH,
                3,
                3,
                Set.of(),
                "FF"
        );

        mockMvc.perform(post("/api/probe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.visited.length()").value(3))
                .andExpect(jsonPath("$.visited[0].x").value(0))
                .andExpect(jsonPath("$.visited[1].y").value(1))
                .andExpect(jsonPath("$.visited[2].y").value(2));
    }

    @Test
    void shouldReturnBadRequestForNullStartPosition() throws Exception {
        String json = """
            {
              "start": null,
              "direction": "NORTH",
              "gridWidth": 5,
              "gridHeight": 5,
              "commands": "F"
            }
            """;

        mockMvc.perform(post("/api/probe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestForInvalidCommandCharacters() throws Exception {
        String jsonRequest = """
            {
              "start": {"x": 0, "y": 0},
              "direction": "NORTH",
              "gridWidth": 5,
              "gridHeight": 5,
              "commands": "FXBLR"
            }
            """;

        mockMvc.perform(post("/api/probe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid command input. Only F, B, L, R are allowed."));
    }

    @Test
    void shouldReturnBadRequestForInvalidDirection() throws Exception {
        String json = """
            {
              "start": {"x": 0, "y": 0},
              "direction": "UPWARD",
              "gridWidth": 5,
              "gridHeight": 5,
              "commands": "F"
            }
            """;

        mockMvc.perform(post("/api/probe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}
