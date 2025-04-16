package com.maveric.submersible.model;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Probe {
    private Position position;
    private Direction direction;
    private final int maxX;
    private final int maxY;
    private final Set<Position> obstacles;
    private final Set<Position> visited = new HashSet<>();

    public Probe(Position position, Direction direction, int maxX, int maxY, Set<Position> obstacles) {
        this.position = position;
        this.direction = direction;
        this.maxX = maxX;
        this.maxY = maxY;
        this.obstacles = obstacles;
        visited.add(position);
    }

    public void execute(String command) {
        if (!command.matches("[FBLR]*")) {
            throw new IllegalArgumentException("Invalid command input. Only F, B, L, R are allowed.");
        }

        for (char c : command.toCharArray()) {
            switch (c) {
                case 'F' -> moveForward();
                case 'B' -> moveBackward();
                case 'L' -> direction = direction.turnLeft();
                case 'R' -> direction = direction.turnRight();
            }
        }
    }

    private void moveForward() {
        Position newPosition = position.moveForward(direction);
        moveIfValid(newPosition);
    }

    private void moveBackward() {
        Position newPosition = position.moveBackward(direction);
        moveIfValid(newPosition);
    }

    private void moveIfValid(Position newPosition) {
        boolean inBounds = newPosition.x() >= 0 && newPosition.x() < maxX
                        && newPosition.y() >= 0 && newPosition.y() < maxY;
        if (inBounds && !obstacles.contains(newPosition)) {
            position = newPosition;
            visited.add(position);
        }
    }
}
