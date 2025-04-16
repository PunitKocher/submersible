package com.maveric.submersible.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    void shouldTurnRightFromNorth() {
        assertEquals(Direction.EAST, Direction.NORTH.turnRight());
    }

    @Test
    void shouldTurnLeftFromNorth() {
        assertEquals(Direction.WEST, Direction.NORTH.turnLeft());
    }

    @Test
    void shouldTurnRightFromWest() {
        assertEquals(Direction.NORTH, Direction.WEST.turnRight());
    }

    @Test
    void shouldTurnLeftFromEast() {
        assertEquals(Direction.NORTH, Direction.EAST.turnLeft());
    }

    @Test
    void shouldTurnRightFromEast() {
        assertEquals(Direction.SOUTH, Direction.EAST.turnRight());
    }

    @Test
    void shouldTurnLeftFromSouth() {
        assertEquals(Direction.EAST, Direction.SOUTH.turnLeft());
    }

    @Test
    void shouldTurnRightFromSouth() {
        assertEquals(Direction.WEST, Direction.SOUTH.turnRight());
    }

    @Test
    void shouldTurnLeftFromWest() {
        assertEquals(Direction.SOUTH, Direction.WEST.turnLeft());
    }

}
