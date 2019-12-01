package agh.cs.project1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MapRepresentation {
    private ArrayList<IMapElement>[][] name;
//    private List<List<List<IMapElement>>> name; ;
    public MapRepresentation(int x, int y)
    {
        this.name = new ArrayList[x][y];
        for(int i = 0; i < x; i++)
            for(int j = 0; j < y; j++)
                this.name[i][j] = new ArrayList<>();
    }

    public void add(IMapElement object)
    {
//        System.out.println(object.getPosition());
        if(object instanceof Grass) {
            this.name[object.getPosition().x][object.getPosition().y].add(object);
        }
        else if(object instanceof Animal) {
            this.name[object.getPosition().x][object.getPosition().y].add(0, object);
        }
    }

    public void remove(IMapElement object, Vector2d position)
    {
//        int x = object.getPosition().x;
//        int y = object.getPosition().y;
        if(object instanceof Grass)
            this.deleteGrassIfPresent(position);
        else{
            this.name[position.x][position.y].remove(object);
//            System.out.println("asdad" + this.name[position.x][position.y].size());
        }
    }

    public void remove(IMapElement object)
    {
        if(object instanceof Grass)
            this.deleteGrassIfPresent(object.getPosition());
        else {
            int x = object.getPosition().x;
            int y = object.getPosition().y;

            this.name[x][y].remove(object);
        }
    }

    public boolean containsKey(Vector2d position)
    {
        return !this.name[position.x][position.y].isEmpty();
    }

    public boolean isGrass(Vector2d position)
    {
        ArrayList<IMapElement> field = this.name[position.x][position.y];
        for(IMapElement element: field)
        {
            if(element instanceof Grass)
                return true;
        }
        return false;
    }

    public void deleteGrassIfPresent(Vector2d position)
    {
        ArrayList<IMapElement> field = this.name[position.x][position.y];
        IMapElement grass = null;
        for(IMapElement element: field)
        {
            if(element instanceof Grass)
                grass = element;
        }
        field.remove(grass);
    }

    public IMapElement getFirstOrNull(Vector2d position) throws ArrayIndexOutOfBoundsException
    {
        try {
            if (this.name[position.x][position.y].isEmpty())
                return null;
            return this.name[position.x][position.y].get(0);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("getFirstOrNull - trying to reach: " + position.x + " " + position.y);
            return null;
        }
    }

    public String toString()
    {
        StringBuilder result = new StringBuilder();
        for (ArrayList<IMapElement>[] arrayLists : this.name) {
            for (ArrayList<IMapElement> arrayList : arrayLists) {
                if (!arrayList.isEmpty()) {
                    for (IMapElement element : arrayList)
                        result.append(element.getPosition());
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }
}
