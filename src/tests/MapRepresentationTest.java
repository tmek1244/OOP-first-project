package tests;

import agh.cs.project1.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapRepresentationTest {


    @Test
    void add() {
        WorldMap map = new WorldMap(10, 10, 4, 4);
        MapRepresentation mapRepresentation = new MapRepresentation(10, 10);
        mapRepresentation.add(new Grass(new Vector2d(1,1)));
        mapRepresentation.add(new Animal(map, new Vector2d(1,2)));
        mapRepresentation.add(new Animal(map, new Vector2d(1,2)));
        System.out.println(mapRepresentation);
    }

    @Test
    void remove() {
    }

    @Test
    void containsKey() {
    }

    @Test
    void isGrass() {
    }

    @Test
    void deleteGrassIfPresent() {
    }
}