package agh.cs.project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Animal implements IMapElement{
    private MapDirection orientation;
    private Vector2d position;
    private WorldMap map;
    private int health;
//    private List<IPositionChangeObserver> observers = new ArrayList<>();
    private int[] gen;

    private int MAX_HEALTH = 20;

    public Animal(WorldMap map)
    {
        this(map, new Vector2d(2,2));
    }

    public Animal(WorldMap map, Vector2d initialPosition)
    {
        this.orientation = MapDirection.NORTH;
        this.position = initialPosition;
        this.map = map;
        this.health = MAX_HEALTH;
        this.gen = this.getRandomGens();
    }

    @Override
    public String toString()
    {
//        return this.orientation.toString();
        return "A";
    }

    public void makeAMove()
    {
        this.turn();
        Vector2d oldPosition = this.position;
//        System.out.println(this.position);
        if(this.moveIfPossible()) {
            this.map.positionChanged(oldPosition, this);
//            this.eatGrassIfIsAt();
        }
    }


    private void turn()
    {
        int whichMove = new Random().nextInt(this.gen.length);
        this.orientation = this.orientation.turnByAngle(whichMove);
    }

    private boolean moveIfPossible()
    {
        Vector2d position = this.map.modulo(this.position.add(this.orientation.toUnitVector()));
        if(!this.isAnyAnimalAt(position))
        {
            this.position = position;
            return true;
        }
        return false;
    }

    private boolean isAnyAnimalAt(Vector2d position)
    {
        return this.map.objectAt(position) instanceof Animal;
    }

    public Vector2d getPosition()
    {
        return this.position;
    }

    public void nextDay()
    {
        this.hurt(1);
        this.removeIfDead();
    }

    public void cure(int amount)
    {
        this.health = Math.min(MAX_HEALTH, this.health + amount);
    }

    public void hurt(int amount) {
        this.health = Math.max(0, this.health - amount);
    }

    private void removeIfDead()
    {
        if(this.health == 0)
            this.map.placeAnimalToGraveyard(this);
    }

    private int [] getRandomGens()
    {
        int[] gens = new int[32];
        for(int i = 0; i < 32; i++)
        {
            gens[i] = new Random().nextInt(8);
        }
        Arrays.sort(gens);
        return gens;
    }
}
