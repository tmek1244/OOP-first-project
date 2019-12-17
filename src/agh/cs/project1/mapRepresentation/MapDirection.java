package agh.cs.project1.mapRepresentation;

public enum MapDirection {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

//    public String toString() {
//        switch (this){
//            case NORTH:
//                return "^";
//            case SOUTH:
//                return "v";
//            case WEST:
//                return "<";
//            case EAST:
//                return ">";
//            default:
//                return null;
//        }
//    }

    public MapDirection previous()
    {
        return MapDirection.values()[(this.ordinal() + 7) % MapDirection.values().length];
    }

    public MapDirection next()
    {
        return MapDirection.values()[(this.ordinal() + 1) % MapDirection.values().length];
    }

    public MapDirection turnByAngle(int angle)
    {
        return MapDirection.values()[(this.ordinal() + angle) % MapDirection.values().length];
    }

    public Vector2d toUnitVector()
    {
        int x = this.ordinal();
        return new Vector2d((int)Math.signum((double)(x - 4)) * (-1) * (x%4)/(Math.max(x%4, 1)),
                (int)Math.signum((double)(((x + 2)%8 - 4)) * (-1) * ((x+2)%4)/(Math.max((x+2)%4, 1))));
    }
}
