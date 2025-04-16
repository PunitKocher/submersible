package com.maveric.submersible.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Position {
    private int x;
    private int y;

    public Position moveForward(Direction direction) {
        return switch (direction) {
            case NORTH -> new Position(x, y + 1);
            case EAST -> new Position(x + 1, y);
            case SOUTH -> new Position(x, y - 1);
            case WEST -> new Position(x - 1, y);
        };
    }

    public Position moveBackward(Direction direction) {
        return switch (direction) {
            case NORTH -> new Position(x, y - 1);
            case EAST -> new Position(x - 1, y);
            case SOUTH -> new Position(x, y + 1);
            case WEST -> new Position(x + 1, y);
        };
    }
}
