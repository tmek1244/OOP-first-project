package agh.cs.project1.mapObject;

import agh.cs.project1.id.IdGenerator;
import agh.cs.project1.mapRepresentation.MapDirection;
import agh.cs.project1.mapRepresentation.Vector2d;
import agh.cs.project1.mapRepresentation.World;
import agh.cs.project1.settings.LoadSettings;

import java.util.Objects;

public class Animal implements IMapElement {
    private MapDirection orientation;
    private Vector2d position;
    private World map;
    private int health;
    private int minHealthToProduce;
    private int ID;

    private Gens gens;

    private static int MAX_HEALTH = LoadSettings.startEnergy;

    public Animal(World map)
    {
        this(map, new Vector2d(2,2), null, null);
    }

    public Animal(World map, Vector2d initialPosition)
    {
        this(map, initialPosition, null, null);
    }

    public Animal(World map, Vector2d initialPosition, Integer health, Gens gens)
    {
        this.ID = IdGenerator.getNextID();
        this.orientation = MapDirection.NORTH;
        this.position = initialPosition;
        this.map = map;
        this.health = Objects.requireNonNullElseGet(health, () -> MAX_HEALTH);
        this.gens = Objects.requireNonNullElseGet(gens, Gens::new);
        this.minHealthToProduce = MAX_HEALTH / 2;
    }


    @Override
    public String toString()
    {
        return "A " + this.getHealth() + " ID: " + this.getID() + " " + this.getPosition();
    }

    public void makeAMove()
    {
        this.turn();

        Vector2d oldPosition = this.position;
        this.position = this.map.modulo(this.position.add(this.orientation.toUnitVector()));

        this.map.positionChanged(this, oldPosition);
    }

    public Vector2d getPosition()
    {
        return this.map.modulo(this.position);
    }

    public void nextDay()
    {
        this.hurt(LoadSettings.moveEnergy);
        this.removeIfDead();
    }

    public Animal produceNewAnimal(Animal other)
    {
        if(other == null)
            return null;
        if(this.minHealthToProduce <= this.getHealth() && other.minHealthToProduce <= other.getHealth())
        {
            Gens gensForKid = new Gens(this.gens, other.gens);
            Vector2d probablePosition, positionForKid = this.position;
            for(int x = 0; x < 8; x++)
            {
                probablePosition = Vector2d.intToDirectionInVector2d(x);
                if(this.isAnyAnimalAt(this.map.modulo(probablePosition.add(this.position))))
                {
                    positionForKid = this.map.modulo(probablePosition.add(this.position));
                    break;
                }
            }

            return new Animal(this.map, positionForKid, this.getHealthForKid() + other.getHealthForKid(), gensForKid);
        }
        return null;
    }

    public int getHealth()
    {
        return this.health;
    }

    public int getID()
    {
        return this.ID;
    }

    public void cure(int amount)
    {
        this.health = Math.min(MAX_HEALTH, this.health + amount);
    }

    private void turn()
    {
        this.orientation = this.orientation.turnByAngle(this.gens.getAngleInInt());
    }

    private boolean isAnyAnimalAt(Vector2d position)
    {
        return this.map.objectAt(position) instanceof Animal;
    }

    private void hurt(int amount) {
        this.health = Math.max(0, this.health - amount);
    }

    private void removeIfDead()
    {
        if(this.health <= 0) {
            this.map.placeAnimalToGraveyard(this);
        }
    }

    private int getHealthForKid()
    {
        this.health = 3*this.health/4;
//        System.out.println(this.health + " " + this);
        return this.health / 3;
    }
}
