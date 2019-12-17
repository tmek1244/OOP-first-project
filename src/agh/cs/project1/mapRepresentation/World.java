package agh.cs.project1.mapRepresentation;

import agh.cs.project1.mapObject.Animal;
import agh.cs.project1.mapObject.Grass;
import agh.cs.project1.mapObject.IMapElement;

import java.util.*;

public class World {
//    private Hashtable<Vector2d, IMapElement> usedMapCoords = new Hashtable<Vector2d, IMapElement>();
    private WorldMap worldMapRepresentation;
    private List<Animal> animals = new ArrayList<>();

    private final Vector2d mapLowerLeft;
    private final Vector2d mapUpperRight;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    private final int jungleArea;
    private int day;

    private Stack<Animal> graveyard = new Stack<>();

    public World(int mapSizeX, int mapSizeY, int jungleSizeX, int jungleSizeY)
    {
        this.day = 0;
        this.mapLowerLeft = new Vector2d(0,0);
        this.mapUpperRight = new Vector2d(mapSizeX, mapSizeY);

        this.jungleLowerLeft = new Vector2d((int)((mapSizeX - jungleSizeX)/2), (int)((mapSizeY - jungleSizeX)/2));
        this.jungleUpperRight = new Vector2d(jungleSizeX, jungleSizeY).add(this.jungleLowerLeft);
        this.jungleArea = jungleSizeX * jungleSizeY;

        this.worldMapRepresentation = new WorldMap(mapSizeX, mapSizeY);
    }

    public void place(IMapElement mapObject)
    {
//        if(!this.isOccupied(mapObject.getPosition()))
//        {
//            this.usedMapCoords.put(mapObject.getPosition(), mapObject);
        this.worldMapRepresentation.add(mapObject);
//            this.usedMapCoords[mapObject.getPosition().x][mapObject.getPosition().y].add(mapObject);
        if(mapObject instanceof Animal)
            this.animals.add((Animal)mapObject);
//            return true;
//        }
//        return false;
    }

    public boolean isOccupied(Vector2d position) {
        return this.worldMapRepresentation.containsKey(position.modulo(this.mapUpperRight));
    }

    public Object objectAt(Vector2d position) {
        return this.worldMapRepresentation.getFirstOrNull(position.modulo(this.mapUpperRight));
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
        this.produceNewShips();
        this.addGrass();
    }

    public void positionChanged(Vector2d oldPosition, Animal thisAnimal)
    {
        this.worldMapRepresentation.remove(thisAnimal, oldPosition);
        this.worldMapRepresentation.add(thisAnimal);
        if(this.worldMapRepresentation.isGrass(thisAnimal.getPosition()))
        {
            thisAnimal.cure(30);
            this.eatGrass(thisAnimal.getPosition());
        }
    }

    public void eatGrass(Vector2d position)
    {
        this.worldMapRepresentation.deleteGrassIfPresent(position);
    }


    private void clearGraveyard()
    {
        while(!this.graveyard.isEmpty()) {
            Animal animal = this.graveyard.pop();
            this.animals.remove(animal);
            this.worldMapRepresentation.remove(animal);
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
        this.worldMapRepresentation.add(inJungle);
        assert grassOnSteppe != null;
        this.worldMapRepresentation.add(new Grass(grassOnSteppe));
    }

    private Vector2d findPlaceForGrass(String partOfMap)
    {
        if(partOfMap.equals("jungle"))
        {
            Vector2d position;
            int i = 0;
            do{
                i += 1;
                int x = (int)(Math.random()*(this.jungleUpperRight.x - this.jungleLowerLeft.x) + this.jungleLowerLeft.x);
                int y = (int)(Math.random()*(this.jungleUpperRight.y - this.jungleLowerLeft.y) + this.jungleLowerLeft.y);
                position = new Vector2d(x, y);
            }while(this.objectAt(position) instanceof Grass && i < this.jungleArea);

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
        return this.worldMapRepresentation.getFirstOrNull(position);
    }

    public int howManyAnimalsAt(Vector2d position)
    {
        return this.worldMapRepresentation.howManyAnimalsAt(position);
    }

    public void produceNewShips()
    {
        List<Vector2d> positions = new ArrayList<>();
        for(Animal animal: this.animals)
            positions.add(animal.getPosition());
        for(Vector2d animalPosition: positions)
        {
            if(this.howManyAnimalsAt(animalPosition) == 2)
            {
                LinkedList<Animal> twoAnimals= this.worldMapRepresentation.getAnimalsOnPosition(animalPosition);
                Animal newAnimal = twoAnimals.getFirst().produceNewAnimal(twoAnimals.getLast());
                if(newAnimal != null)
                    this.place(newAnimal);
            }
        }
    }
}
