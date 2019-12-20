package tests;

import agh.cs.project1.mapObject.Animal;
import agh.cs.project1.mapObject.Grass;
import agh.cs.project1.mapRepresentation.Vector2d;
import agh.cs.project1.mapRepresentation.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorldTest {
    @Test
    void worldTest() {
        World world = new World(3, 3, 1, 1);
        Animal animalToAdd = new Animal(world, new Vector2d(0,0));
        world.place(new Grass(new Vector2d(0,0)));
        assertTrue(world.objectAt(new Vector2d(0,0)) instanceof Grass);
        world.place(new Animal(world, new Vector2d(0,0), 15, null));
        world.place(new Animal(world, new Vector2d(0,0), 15, null));
        world.place(new Animal(world, new Vector2d(0,0), 50, null));
        assertEquals(3, world.howManyAnimalsAt(new Vector2d(0, 0)));
        world.place(animalToAdd);
        assertEquals(4, world.howManyAnimalsAt(new Vector2d(0,0)));
        world.placeAnimalToGraveyard(animalToAdd);
        world.printTreeAt(new Vector2d(0,0));
        world.nextDay();
        assertEquals(0, world.howManyAnimalsAt(new Vector2d(0, 0)));
        assertEquals(3, world.howManyAnimalsLive());
    }
}