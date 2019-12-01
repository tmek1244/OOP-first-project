package agh.cs.project1;

import javax.swing.*;
import java.lang.reflect.Member;
import java.util.*;

public class WorldMap {
//    private Hashtable<Vector2d, IMapElement> usedMapCoords = new Hashtable<Vector2d, IMapElement>();
    private MapRepresentation usedMapCoords;
    private List<Animal> animals = new ArrayList<>();

    private final Vector2d mapLowerLeft;
    private final Vector2d mapUpperRight;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    private int day;

    private Stack<Animal> graveyard = new Stack<>();

    public WorldMap(int mapSizeX, int mapSizeY, int jungleSizeX, int jungleSizeY)
    {
        this.day = 0;
        this.mapLowerLeft = new Vector2d(0,0);
        this.mapUpperRight = new Vector2d(mapSizeX, mapSizeY);

        this.jungleLowerLeft = new Vector2d((int)((mapSizeX - jungleSizeX)/2), (int)((mapSizeY - jungleSizeX)/2));
        this.jungleUpperRight = new Vector2d(jungleSizeX, jungleSizeY).add(this.jungleLowerLeft);

        this.usedMapCoords = new MapRepresentation(mapSizeX, mapSizeY);
    }

    public void place(IMapElement mapObject)
    {
//        if(!this.isOccupied(mapObject.getPosition()))
//        {
//            this.usedMapCoords.put(mapObject.getPosition(), mapObject);
        this.usedMapCoords.add(mapObject);
//            this.usedMapCoords[mapObject.getPosition().x][mapObject.getPosition().y].add(mapObject);
        if(mapObject instanceof Animal)
            this.animals.add((Animal)mapObject);
//            return true;
//        }
//        return false;
    }

    public boolean isOccupied(Vector2d position) {
        return this.usedMapCoords.containsKey(position.modulo(this.mapUpperRight));
    }
//
//    public boolean canMoveTo(Vector2d position)
//    {
//
//    }

    public Object objectAt(Vector2d position) {
        return this.usedMapCoords.getFirstOrNull(position.modulo(this.mapUpperRight));
    }

    public Vector2d modulo(Vector2d other)
    {
        return other.modulo(this.mapUpperRight);
    }

    public void nextDay()
    {
        this.day += 1;
//        System.out.println("pozostalo " + this.animals.size() + " zwierzat!");
        for(Animal animal: this.animals)
        {
            animal.nextDay();
        }
        this.clearGraveyard();
        for(Animal animal: this.animals)
        {
            animal.makeAMove();
        }
        this.addGrass();
    }

    public void positionChanged(Vector2d oldPosition, Animal thisAnimal)
    {
        this.usedMapCoords.remove(thisAnimal, oldPosition);
        this.usedMapCoords.add(thisAnimal);
        if(this.usedMapCoords.isGrass(thisAnimal.getPosition()))
        {
            thisAnimal.cure(5);
            this.eatGrass(thisAnimal.getPosition());
        }
    }

    public void eatGrass(Vector2d position)
    {
        this.usedMapCoords.deleteGrassIfPresent(position);
    }


    private void clearGraveyard()
    {
        while(!this.graveyard.isEmpty()) {
            Animal animal = this.graveyard.pop();
            this.animals.remove(animal);
            this.usedMapCoords.remove(animal);
        }
    }

    public void placeAnimalToGraveyard(Animal animal)
    {
        this.graveyard.push(animal);
    }

    private void addGrass()
    {
        Vector2d grassInJungle = findPlaceForGrass("jungle");
        Vector2d grassOnSteppe = findPlaceForGrass("steppe");
        assert grassInJungle != null;
        Grass inJungle = new Grass(grassInJungle);
        this.usedMapCoords.add(inJungle);
        assert grassOnSteppe != null;
        this.usedMapCoords.add(new Grass(grassOnSteppe));
    }

    private Vector2d findPlaceForGrass(String partOfMap)
    {
        if(partOfMap.equals("jungle"))
        {
            Vector2d position;

            do{
                int x = (int)(Math.random()*(this.jungleUpperRight.x - this.jungleLowerLeft.x) + this.jungleLowerLeft.x);
                int y = (int)(Math.random()*(this.jungleUpperRight.y - this.jungleLowerLeft.y) + this.jungleLowerLeft.y);
                position = new Vector2d(x, y);
            }while(this.objectAt(position) instanceof Grass);

            return position;
        }
        else if(partOfMap.equals("steppe"))
        {
            Vector2d position;

            do{
                int x = (int)(Math.random()*this.mapUpperRight.x);
                int y = (int)(Math.random()*this.mapUpperRight.y);
                position = new Vector2d(x, y);
            }while(this.objectAt(position) instanceof Grass
                    || (position.follows(this.jungleLowerLeft) && position.precedes(this.jungleUpperRight)));
            return position;
        }
        return null;
    }

    public IMapElement getElementOnPosition(Vector2d position)
    {
        return this.usedMapCoords.getFirstOrNull(position);
    }

    public MapRepresentation getMapRepresentation()
    {
        return this.usedMapCoords;
    }
}
