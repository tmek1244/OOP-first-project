package agh.cs.project1.mapRepresentation;

import java.util.Random;

public enum MapDirection {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

    public MapDirection previous()
    {
        return MapDirection.values()[(this.ordinal() + 7) % MapDirection.values().length];
    }

    public MapDirection next()
    {
        return MapDirection.values()[(this.ordinal() + 1) % MapDirection.values().length];
    }

    public MapDirection turnByAngle(int angleInInt)
    {
        return MapDirection.values()[(this.ordinal() + angleInInt) % MapDirection.values().length];
    }

    public static MapDirection getRandomDirection()
    {
        return MapDirection.values()[new Random().nextInt(8)];
    }

    public Vector2d toUnitVector()
    {
        return Vector2d.intToDirectionInVector2d(this.ordinal());
    }
}
