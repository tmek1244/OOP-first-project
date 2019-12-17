package agh.cs.project1.mapObject;

import agh.cs.project1.mapRepresentation.MapDirection;
import agh.cs.project1.mapRepresentation.Vector2d;
import agh.cs.project1.mapRepresentation.World;

import java.util.Objects;

public class Animal implements IMapElement {
    private MapDirection orientation;
    private Vector2d position;
    private World map;
    private int health;
    private int minHealthToProduce;

    private Gens gens;

    private int MAX_HEALTH = 100;

    public Animal(World map)
    {
        this(map, new Vector2d(2,2));
    }

    public Animal(World map, Vector2d initialPosition)
    {
        this(map, initialPosition, null, null);
    }

    public Animal(World map, Vector2d initialPosition, Integer health, Gens gens)
    {
        this.orientation = MapDirection.NORTH;
        this.position = initialPosition;
        this.map = map;
        this.health = Objects.requireNonNullElseGet(health, () -> MAX_HEALTH);
        this.gens = Objects.requireNonNullElseGet(gens, Gens::new);
        this.minHealthToProduce = this.MAX_HEALTH / 2;
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
        if(this.moveIfPossible()) {
            this.map.positionChanged(oldPosition, this);
//            this.eatGrassIfIsAt();
        }
    }


    private void turn()
    {
        this.orientation = this.orientation.turnByAngle(this.gens.getAngle());
    }

    private boolean moveIfPossible()
    {
        Vector2d position = this.map.modulo(this.position.add(this.orientation.toUnitVector()));
        if(this.howManyAnimalsAt(position) < 10)
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

    private int howManyAnimalsAt(Vector2d position)
    {
        return this.map.howManyAnimalsAt(position);
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

    private int healthForKid()
    {
        this.health = 3*this.health/4;
        return this.health / 3;
    }

    public Animal produceNewAnimal(Animal other)
    {
        if(this.minHealthToProduce <= this.health && other.minHealthToProduce <= other.health)
        {
            Gens gensForKid = new Gens(this.gens, other.gens);
            return new Animal(this.map, this.position, this.healthForKid() + other.healthForKid(), gensForKid);
        }
        return null;
    }
}
