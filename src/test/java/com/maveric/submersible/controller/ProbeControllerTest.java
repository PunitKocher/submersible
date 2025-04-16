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
        CommandRequest request = new CommandRequest();
        request.setStart(new Position(1, 1));
        request.setDirection(Direction.NORTH);
        request.setGridWidth(5);
        request.setGridHeight(5);
        request.setObstacles(Set.of(new Position(3, 3)));
        request.setCommands("FFRFF");

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
        CommandRequest request = new CommandRequest();
        request.setStart(new Position(0, 0));
        request.setDirection(Direction.EAST);
        request.setGridWidth(5);
        request.setGridHeight(5);
        request.setObstacles(Set.of());
        request.setCommands("FFRFF");

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
        CommandRequest request = new CommandRequest();
        request.setStart(new Position(0, 0));
        request.setDirection(Direction.EAST);
        request.setGridWidth(5);
        request.setGridHeight(5);
        request.setObstacles(Set.of(new Position(2, 0)));
        request.setCommands("FFF");

        mockMvc.perform(post("/api/probe/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current.x").value(1))  // Blocked at x=2
                .andExpect(jsonPath("$.current.y").value(0));
    }

    @Test
    void shouldNotMoveOutOfGrid() throws Exception {
        CommandRequest request = new CommandRequest();
        request.setStart(new Position(0, 0));
        request.setDirection(Direction.WEST);
        request.setGridWidth(3);
        request.setGridHeight(3);
        request.setObstacles(Set.of());
        request.setCommands("F"); // Would go to x = -1

        mockMvc.perform(post("/api/probe/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.current.x").value(0)); // stays in bounds
    }

    @Test
    void shouldOnlyRotateAndNotMove() throws Exception {
        CommandRequest request = new CommandRequest();
        request.setStart(new Position(2, 2));
        request.setDirection(Direction.NORTH);
        request.setGridWidth(5);
        request.setGridHeight(5);
        request.setObstacles(Set.of());
        request.setCommands("RRLL");

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
        CommandRequest request = new CommandRequest();
        request.setStart(new Position(0, 0));
        request.setDirection(Direction.NORTH);
        request.setGridWidth(3);
        request.setGridHeight(3);
        request.setObstacles(Set.of());
        request.setCommands("FF");

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
