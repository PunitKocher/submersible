package com.maveric.submersible.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProbeTest {

    @Test
    void shouldMoveForwardOnEmptyGrid() {
        Probe probe = new Probe(
            new Position(1, 1),
            Direction.NORTH,
            5, 5,
            Set.of()
        );

        probe.execute("F");

        assertEquals(new Position(1, 2), probe.getPosition());
        assertEquals(Direction.NORTH, probe.getDirection());
    }

    @Test
    void shouldTurnLeftAndMove() {
        Probe probe = new Probe(new Position(1, 1), Direction.NORTH, 5, 5, Set.of());
        probe.execute("L");
        probe.execute("F");

        assertEquals(new Position(0, 1), probe.getPosition());
        assertEquals(Direction.WEST, probe.getDirection());
    }

    @Test
    void shouldNotMoveIntoObstacle() {
        Probe probe = new Probe(new Position(1, 1), Direction.NORTH, 5, 5, Set.of(new Position(1, 2)));
        probe.execute("F");

        // Should not move
        assertEquals(new Position(1, 1), probe.getPosition());
    }

    @Test
    void shouldReturnToSamePosition() {
        Probe probe = new Probe(new Position(1, 1), Direction.NORTH, 5, 5, Set.of());
        probe.execute("F");   // (1, 2)
        probe.execute("R");   // EAST
        probe.execute("R");   // SOUTH
        probe.execute("L");   // EAST
        probe.execute("R");   // SOUTH
        probe.execute("F");   // (1, 1) ✅
    
        assertEquals(new Position(1, 1), probe.getPosition());
    }

    @Test
    void shouldFaceSouthAndMoveCorrectly() {
        Probe probe = new Probe(new Position(1, 1), Direction.NORTH, 5, 5, Set.of());
        
        probe.execute("R"); // NORTH → EAST
        probe.execute("F"); // move to (2,1)
        probe.execute("R"); // EAST → SOUTH
        probe.execute("F"); // move to (2,0)
        
        assertEquals(new Position(2, 0), probe.getPosition());
        assertEquals(Direction.SOUTH, probe.getDirection());
    }

    @Test
    void shouldNotMoveOutsideGridNorth() {
        Probe probe = new Probe(new Position(2, 4), Direction.NORTH, 5, 5, Set.of());
        probe.execute("F");
        assertEquals(new Position(2, 4), probe.getPosition());
    }

}

