package com.maveric.submersible.dto;

import com.maveric.submersible.model.Direction;
import com.maveric.submersible.model.Position;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Data
public class CommandRequest {
    @NotNull(message = "Start position cannot be null")
    private Position start;
    @NotNull(message = "Direction cannot be null")
    private Direction direction;
    @NotNull(message = "Grid width cannot be null")
    private int gridWidth;
    @NotNull(message = "Grid height cannot be null")
    private int gridHeight;
    private Set<Position> obstacles;
    @NotNull(message = "Commands cannot be null")
    private String commands;
}
