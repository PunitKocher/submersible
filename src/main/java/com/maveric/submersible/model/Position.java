package com.maveric.submersible.model;

public record Position(int x, int y) {

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
