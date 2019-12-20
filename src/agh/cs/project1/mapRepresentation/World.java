package agh.cs.project1.mapRepresentation;

import agh.cs.project1.mapObject.Animal;
import agh.cs.project1.mapObject.Grass;
import agh.cs.project1.mapObject.IMapElement;

import java.util.*;

public class World {
    private WorldMap worldMap;
    private List<Animal> animals = new ArrayList<>();

    private final Vector2d mapUpperRight;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    private final int jungleArea;
    private int dayCounter;

    private Stack<Animal> graveyard = new Stack<>();

    public World(int mapSizeX, int mapSizeY, int jungleSizeX, int jungleSizeY)
    {
        this.dayCounter = 0;

        this.mapUpperRight = new Vector2d(mapSizeX, mapSizeY);
        this.jungleLowerLeft = new Vector2d((mapSizeX - jungleSizeX)/2, (mapSizeY - jungleSizeY)/2);
        this.jungleUpperRight = new Vector2d(jungleSizeX, jungleSizeY).add(this.jungleLowerLeft);
        this.jungleArea = jungleSizeX * jungleSizeY;

        this.worldMap = new WorldMap(mapSizeX, mapSizeY);
    }

    public void place(IMapElement newMapElement)
    {
        this.worldMap.add(newMapElement);
        if(newMapElement instanceof Animal)
            this.animals.add((Animal)newMapElement);
    }

    public boolean isOccupied(Vector2d position) {
        return this.worldMap.containsKey(position);
    }

    public IMapElement objectAt(Vector2d position) {
        return this.worldMap.tryToGetFirstElement(position);
    }

    public Vector2d modulo(Vector2d position)
    {
        return position.modulo(this.mapUpperRight);
    }

    public void positionChanged(Animal thisAnimal, Vector2d oldPosition)
    {
        this.worldMap.remove(thisAnimal, oldPosition);
        this.worldMap.add(thisAnimal);
    }

    public IMapElement getElementOnPosition(Vector2d position)
    {
        return this.worldMap.tryToGetFirstElement(position);
    }

    public int howManyAnimalsAt(Vector2d position)
    {
        return this.worldMap.howManyAnimalsAt(position);
    }

    public int howManyAnimalsLive()
    {
        return this.animals.size();
    }

    public void placeAnimalToGraveyard(Animal animal)
    {
        this.graveyard.push(animal);
    }

    public void printTreeAt(Vector2d position)
    {
        this.worldMap.printTreeAt(position);
    }

    public void nextDay()
    {
        this.dayCounter += 1;
        this.removeDeadAnimals();
        this.moveAllAnimals();
        this.eatGrass();
        this.produceNewShips();
        this.addGrass();
    }

    private void removeDeadAnimals()
    {
        for(Animal animal: this.animals)
            animal.nextDay();

        this.clearGraveyard();
    }

    private void moveAllAnimals()
    {
        for(Animal animal: this.animals)
            animal.makeAMove();
    }

    private void eatGrass()
    {
        for(Vector2d animalPosition: this.getAnimalsPositions())
            this.eatGrassAt(animalPosition);
    }

    private void produceNewShips()
    {
        for(Vector2d animalPosition: this.getAnimalsPositions())
        {
            if(this.howManyAnimalsAt(animalPosition) >= 2)
            {
                Animal firstAnimal = this.worldMap.tryToGetFirstAnimalAt(animalPosition);
                Animal secondAnimal = this.worldMap.tryToGetSecondAnimalAt(animalPosition);
                if(secondAnimal == null)
                    continue;
                this.worldMap.remove(firstAnimal);
                this.worldMap.remove(secondAnimal);
                Animal newAnimal = firstAnimal.produceNewAnimal(secondAnimal);

                if(newAnimal != null) {
                    this.place(newAnimal);
                }
                this.worldMap.add(firstAnimal);
                this.worldMap.add(secondAnimal);
            }
        }
    }

    private void addGrass()
    {
        Vector2d grassInJungle = findPlaceForGrass("jungle");
        Vector2d grassOnSteppe = findPlaceForGrass("steppe");
        assert grassInJungle != null;
        Grass inJungle = new Grass(grassInJungle);
        this.worldMap.add(inJungle);
        assert grassOnSteppe != null;
        this.worldMap.add(new Grass(grassOnSteppe));
    }

    private void eatGrassAt(Vector2d position)
    {
        this.worldMap.eatGrassIfPossible(position);
    }

    private void clearGraveyard()
    {
        while(!this.graveyard.isEmpty()) {
            Animal animal = this.graveyard.pop();
            this.animals.remove(animal);
            this.worldMap.remove(animal);
        }
    }

    private Vector2d findPlaceForGrass(String partOfMap)
    {
        if(partOfMap.equals("jungle"))
        {
            Vector2d position;
            int i = 0;
            do{
                i += 1;
                int x = (int)Math.round((Math.random()*(this.jungleUpperRight.x - this.jungleLowerLeft.x)) + this.jungleLowerLeft.x);
                int y = (int)Math.round((Math.random()*(this.jungleUpperRight.y - this.jungleLowerLeft.y)) + this.jungleLowerLeft.y);
                position = new Vector2d(x, y);
            }while(this.worldMap.isGrassAt(position) && i < this.jungleArea);

            return position;
        }
        else if(partOfMap.equals("steppe"))
        {
            Vector2d position;

            do{
                int x = (int)(Math.random()*this.mapUpperRight.x);
                int y = (int)(Math.random()*this.mapUpperRight.y);
                position = new Vector2d(x, y);
            }while(this.worldMap.isGrassAt(position)
                    || (position.follows(this.jungleLowerLeft) && position.precedes(this.jungleUpperRight)));
            return position;
        }
        return null;
    }

    private Set<Vector2d> getAnimalsPositions()
    {
        Set<Vector2d> positions = new TreeSet<>();

        for(Animal animal: this.animals)
            positions.add(animal.getPosition());
        return positions;
    }
}
