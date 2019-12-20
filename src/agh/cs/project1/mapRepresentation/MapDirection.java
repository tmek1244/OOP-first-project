package agh.cs.project1.mapRepresentation;

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

    public Vector2d toUnitVector()
    {
        return Vector2d.intToDirectionInVector2d(this.ordinal());
    }
}
