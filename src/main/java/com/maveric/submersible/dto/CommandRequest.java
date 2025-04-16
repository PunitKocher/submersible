package com.maveric.submersible.dto;

import com.maveric.submersible.model.Direction;
import com.maveric.submersible.model.Position;
import lombok.Data;

import java.util.Set;

@Data
public class CommandRequest {
    private Position start;
    private Direction direction;
    private int gridWidth;
    private int gridHeight;
    private Set<Position> obstacles;
    private String commands;
}
