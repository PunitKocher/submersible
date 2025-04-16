package com.maveric.submersible.dto;

import com.maveric.submersible.model.Direction;
import com.maveric.submersible.model.Position;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CommandRequest(
    @NotNull Position start,
    @NotNull Direction direction,
    @NotNull int gridWidth,
    @NotNull int gridHeight,
    Set<Position> obstacles,
    @NotNull String commands
) {}

