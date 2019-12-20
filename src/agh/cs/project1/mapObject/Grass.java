package agh.cs.project1.mapObject;

import agh.cs.project1.mapRepresentation.Vector2d;
import agh.cs.project1.settings.LoadSettings;

public class Grass implements IMapElement {
    private Vector2d position;
    private final int health = LoadSettings.plantEnergy;

    public Grass(Vector2d position)
    {
        this.position = position;
    }

    public Vector2d getPosition()
    {
        return this.position;
    }

    public String toString()
    {
        return "G";
    }

    public int getHealth(){
        return this.health;
    }
}
