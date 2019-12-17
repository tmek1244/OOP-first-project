package tests;

import agh.cs.project1.mapRepresentation.Vector2d;

import static org.junit.jupiter.api.Assertions.*;

import static agh.cs.project1.mapRepresentation.MapDirection.*;

class MapDirectionTest {

    @org.junit.jupiter.api.Test
    void previous() {
        assertEquals(NORTH_WEST, NORTH.previous());
        assertEquals(WEST, NORTH_WEST.previous());
        assertEquals(SOUTH, SOUTH_WEST.previous());
        assertEquals(NORTH_EAST, EAST.previous());
    }

    @org.junit.jupiter.api.Test
    void next() {
        assertEquals(NORTH_EAST, NORTH.next());
        assertEquals(NORTH, NORTH_WEST.next());
        assertEquals(WEST, SOUTH_WEST.next());
        assertEquals(SOUTH_EAST, EAST.next());
    }

    @org.junit.jupiter.api.Test
    void toUnitVector() {
        assertEquals(new Vector2d(0, 1), NORTH.toUnitVector());
        assertEquals(new Vector2d(1, 1), NORTH_EAST.toUnitVector());
        assertEquals(new Vector2d(1, 0), EAST.toUnitVector());
        assertEquals(new Vector2d(1, -1), SOUTH_EAST.toUnitVector());
        assertEquals(new Vector2d(0, -1), SOUTH.toUnitVector());
        assertEquals(new Vector2d(-1, -1), SOUTH_WEST.toUnitVector());
        assertEquals(new Vector2d(-1, 0), WEST.toUnitVector());
        assertEquals(new Vector2d(-1, 1), NORTH_WEST.toUnitVector());

    }
}