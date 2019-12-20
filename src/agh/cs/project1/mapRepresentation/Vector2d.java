package agh.cs.project1.mapRepresentation;

public class Vector2d implements Comparable<Vector2d>
{
    public final int x;
    public final int y;

    public Vector2d(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return "(" + this.x + "," + this.y + ")";
    }

    public Vector2d add(Vector2d other)
    {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }

    public Vector2d modulo(Vector2d other)
    {
        return new Vector2d((this.x + other.x)% other.x, (this.y + other.y) % other.y);
    }

    public static Vector2d intToDirectionInVector2d(int x)
    {
        return new Vector2d((int)Math.signum((double)(x - 4)) * (-1) * (x%4)/(Math.max(x%4, 1)),
                (int)Math.signum((double)(((x + 2)%8 - 4)) * (-1) * ((x+2)%4)/(Math.max((x+2)%4, 1))));
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int compareTo(Vector2d other) {
        if(this.equals(other))
            return 0;
        if(this.x > other.x)
            return 1;
        if(other.x > this.x)
            return -1;
        if(this.y > other.y)
            return 1;
        return -1;
    }

    boolean precedes(Vector2d other)
    {
        return (this.x <= other.x && this.y <= other.y);
    }

    boolean follows(Vector2d other)
    {
        return (this.x >= other.x && this.y >= other.y);
    }
}
