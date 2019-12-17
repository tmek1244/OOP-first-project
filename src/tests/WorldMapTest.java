package tests;

import agh.cs.project1.mapObject.Animal;
import agh.cs.project1.mapObject.Grass;
import agh.cs.project1.mapRepresentation.WorldMap;
import agh.cs.project1.mapRepresentation.Vector2d;
import agh.cs.project1.mapRepresentation.World;
import org.junit.jupiter.api.Test;

class WorldMapTest {


    @Test
    void add() {
        World map = new World(10, 10, 4, 4);
        WorldMap worldMap = new WorldMap(10, 10);
        worldMap.add(new Grass(new Vector2d(1,1)));
        worldMap.add(new Animal(map, new Vector2d(1,2)));
        worldMap.add(new Animal(map, new Vector2d(1,2)));
        System.out.println(worldMap);
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