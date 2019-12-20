package agh.cs.project1.id;

public class IdGenerator {
    private static int nextID = 0;

    public static int getNextID()
    {
        nextID += 1;
        return nextID - 1;
    }
}
