package com.maveric.submersible.model;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public Direction turnRight() {
        return values()[(this.ordinal() + 1) % 4];
    }

    public Direction turnLeft() {
        return values()[(this.ordinal() + 3) % 4];
    }
}
