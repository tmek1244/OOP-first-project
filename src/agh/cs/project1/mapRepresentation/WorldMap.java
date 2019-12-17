package agh.cs.project1.mapRepresentation;

import agh.cs.project1.mapObject.Animal;
import agh.cs.project1.mapObject.Grass;
import agh.cs.project1.mapObject.IMapElement;
import agh.cs.project1.mapRepresentation.Vector2d;

import java.util.ArrayList;
import java.util.LinkedList;

public class WorldMap {
    private ArrayList<ArrayList<LinkedList<IMapElement>>> name;

    public WorldMap(int x, int y)
    {
        this.name = new ArrayList<>(x);
        for(int i = 0; i < x; i++) {
            this.name.add(new ArrayList<>(y));
            for(int j = 0; j < y; j++)
            {
                this.name.get(i).add(new LinkedList<>());
            }
        }
        System.out.println(this.name.get(10).size());
    }

    public void add(IMapElement object)
    {
//        System.out.println(object.getPosition());
        if(object instanceof Grass) {
            this.name.get(object.getPosition().x).get(object.getPosition().y).add(object);
        }
        else if(object instanceof Animal) {
            this.name.get(object.getPosition().x).get(object.getPosition().y).add(0, object);
        }
    }

    public void remove(IMapElement object, Vector2d position)
    {
//        int x = object.getPosition().x;
//        int y = object.getPosition().y;
        if(object instanceof Grass)
            this.deleteGrassIfPresent(position);
        else{
            this.name.get(position.x).get(position.y).remove(object);
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

            this.name.get(x).get(y).remove(object);
        }
    }

    public boolean containsKey(Vector2d position)
    {
        return !this.name.get(position.x).get(position.y).isEmpty();
    }

    public boolean isGrass(Vector2d position)
    {
        LinkedList<IMapElement> field = this.name.get(position.x).get(position.y);
        for(IMapElement element: field)
        {
            if(element instanceof Grass)
                return true;
        }
        return false;
    }

    public void deleteGrassIfPresent(Vector2d position)
    {
        LinkedList<IMapElement> field = this.name.get(position.x).get(position.y);
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
            if (this.name.get(position.x).get(position.y).isEmpty())
                return null;
            return this.name.get(position.x).get(position.y).get(0);
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
        for (ArrayList<LinkedList<IMapElement>> arrayLists : this.name) {
            for (LinkedList<IMapElement> arrayList : arrayLists) {
                if (!arrayList.isEmpty()) {
                    for (IMapElement element : arrayList)
                        result.append(element.getPosition());
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }

    public int howManyAnimalsAt(Vector2d position)
    {
        return this.name.get(position.x).get(position.y).size() - (this.isGrass(position) ? 1 : 0);
    }

    public LinkedList<Animal> getAnimalsOnPosition(Vector2d position)
    {
        LinkedList<Animal> animalsAtThisPosition = new LinkedList<>();
        for(IMapElement object: this.name.get(position.x).get(position.y))
        {
            if(object instanceof Animal)
                animalsAtThisPosition.add((Animal)object);
        }
        return animalsAtThisPosition;
    }
}
