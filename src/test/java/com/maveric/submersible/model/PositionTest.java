package com.maveric.submersible.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    void shouldMoveForwardFacingNorth() {
        Position startPosition = new Position(0, 0);
        Position result = startPosition.moveForward(Direction.NORTH);
        assertEquals(new Position(0, 1), result);    
    }

    @Test
    void shouldMoveForwardFacingEast() {
        Position startPosition = new Position(1, 1);
        Position result = startPosition.moveForward(Direction.EAST);
        assertEquals(new Position(2, 1), result);    
    }

    @Test
    void shouldMoveForwardFacingWest() {
        Position startPosition = new Position(0, 1);
        Position result = startPosition.moveForward(Direction.WEST);
        assertEquals(new Position(-1, 1), result);    
    }

    @Test
    void shouldMoveForwardFacingSouth() {
        Position startPosition = new Position(0, 1);
        Position result = startPosition.moveForward(Direction.WEST);
        assertEquals(new Position(-1, 1), result);    
    }

    @Test
    void shouldMoveBackwardFacingNorth() {
        Position startPosition = new Position(2, 2);
        Position result = startPosition.moveBackward(Direction.NORTH);
        assertEquals(new Position(2, 1), result);    
    }

    @Test
    void shouldMoveBackwardFacingEast() {
        Position startPosition = new Position(3, 6);
        Position result = startPosition.moveBackward(Direction.EAST);
        assertEquals(new Position(2, 6), result);    
    }

    @Test
    void shouldMoveBackwardFacingWest() {
        Position startPosition = new Position(-10, 5);
        Position result = startPosition.moveBackward(Direction.WEST);
        assertEquals(new Position(-9, 5), result);    
    }

    @Test
    void shouldMoveBackwardFacingSouth() {
        Position startPosition = new Position(5, 8);
        Position result = startPosition.moveBackward(Direction.SOUTH);
        assertEquals(new Position(5, 9), result);    
    }

}

