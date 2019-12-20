package tests;

import agh.cs.project1.mapRepresentation.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void modulo() {
        Vector2d moduloThis = new Vector2d(10, 5);
        assertEquals(new Vector2d(0, 0), new Vector2d(10, 5).modulo(moduloThis));
        assertEquals(new Vector2d(9, 0), new Vector2d(-1, 5).modulo(moduloThis));
        assertEquals(new Vector2d(9, 3), new Vector2d(-1, -2).modulo(moduloThis));
        assertEquals(new Vector2d(1, 4), new Vector2d(11, -1).modulo(moduloThis));
        assertEquals(new Vector2d(2, 2), new Vector2d(12, -3).modulo(moduloThis));
    }

    @Test
    void intToDirectionInVector2d() {
        assertEquals(new Vector2d(0, 1), Vector2d.intToDirectionInVector2d(0));
        assertEquals(new Vector2d(1, 1), Vector2d.intToDirectionInVector2d(1));
        assertEquals(new Vector2d(1, 0), Vector2d.intToDirectionInVector2d(2));
        assertEquals(new Vector2d(1, -1), Vector2d.intToDirectionInVector2d(3));
        assertEquals(new Vector2d(0, -1), Vector2d.intToDirectionInVector2d(4));
        assertEquals(new Vector2d(-1, -1), Vector2d.intToDirectionInVector2d(5));
        assertEquals(new Vector2d(-1, 0), Vector2d.intToDirectionInVector2d(6));
        assertEquals(new Vector2d(-1, 1), Vector2d.intToDirectionInVector2d(7));
    }
}