package agh.cs.project1.mapRepresentation;

import agh.cs.project1.mapObject.Animal;
import agh.cs.project1.mapObject.Grass;
import agh.cs.project1.mapObject.IMapElement;

import java.util.*;

class WorldMap {
    private ArrayList<ArrayList<TreeSet<IMapElement>>> board;

    WorldMap(int width, int height)
    {
        this.board = new ArrayList<>(width);
        for(int i = 0; i < width; i++) {
            this.board.add(new ArrayList<>(height));
            for (int j = 0; j < height; j++) {
                this.board.get(i).add(new TreeSet<>((e1, e2) -> {
                    if (e1 instanceof Grass)
                        return 1;
                    if (e2 instanceof Grass)
                        return -1;
                    if (areEquallyStrong(e1, e2)) {
                        return Integer.compare(((Animal) e1).getID(), ((Animal) e2).getID());
                    }
                    return (-1)*Integer.compare(((Animal) e1).getHealth(), ((Animal) e2).getHealth());
                }));
            }
        }
    }

    void add(IMapElement newMapElement)
    {
        if(newMapElement instanceof Grass && this.isGrassAt(newMapElement.getPosition()))
            return;
        getTreeSetAt(newMapElement.getPosition()).add(newMapElement);
    }

    void remove(IMapElement object, Vector2d position)
    {
        getTreeSetAt(position).remove(object);
    }

    void remove(IMapElement object)
    {
        this.remove(object, object.getPosition());
    }

    boolean containsKey(Vector2d position)
    {
        return !getTreeSetAt(position).isEmpty();
    }

    boolean isGrassAt(Vector2d position)
    {
        TreeSet<IMapElement> field = getTreeSetAt(position);
        if(field.isEmpty())
            return false;
        return field.last() instanceof Grass;
    }

    void eatGrassIfPossible(Vector2d position)
    {
        if(this.isGrassAt(position))
        {
            int fullHealthToGiveAway = Objects.requireNonNull(getGrassAndRemove(position)).getHealth();
            this.addHealthToTheStrongest(position, fullHealthToGiveAway/this.howManyToShare(position));
        }
    }

    int howManyAnimalsAt(Vector2d position)
    {
        return getTreeSetAt(position).size() - (this.isGrassAt(position) ? 1 : 0);
    }

    IMapElement tryToGetFirstElement(Vector2d position) throws ArrayIndexOutOfBoundsException
    {
        try {
            if(getTreeSetAt(position).isEmpty())
                return null;
            return getTreeSetAt(position).first();
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("getFirstOrNull - trying to reach: " + position.x + " " + position.y);
            return null;
        }
    }

    Animal tryToGetFirstAnimalAt(Vector2d position)
    {
        if(this.howManyAnimalsAt(position) < 1)
            return null;
        return (Animal)getTreeSetAt(position).first();
    }

    Animal tryToGetSecondAnimalAt(Vector2d position)
    {
        if(this.howManyAnimalsAt(position) < 2)
            return null;
        Iterator<IMapElement> it = getTreeSetAt(position).iterator();
        it.next();
        return (Animal)it.next();
    }

    private void addHealthToTheStrongest(Vector2d position, int amountOfHealth)
    {
        int theStrongestHealth = this.tryToGetFirstAnimalAt(position).getHealth();
        for(Animal animal: this.getAnimalsAt(position))
        {
            if(animal.getHealth() == theStrongestHealth)
                animal.cure(amountOfHealth);
            else break;
        }
    }

    private int howManyToShare(Vector2d position)
    {
        ArrayList<Animal> animals = getAnimalsAt(position);
        int howManyAnimalsToShare = 0;
        for (IMapElement animal : animals) {
            if (areEquallyStrong(animal, animals.get(0)))
                howManyAnimalsToShare += 1;
            else
                break;
        }
        return howManyAnimalsToShare;
    }

    private boolean areEquallyStrong(IMapElement firstAnimal, IMapElement secondAnimal)
    {
        if(firstAnimal instanceof Animal && secondAnimal instanceof Animal)
            return ((Animal)firstAnimal).getHealth() == ((Animal)secondAnimal).getHealth();
        return false;
    }

    private Grass getGrassAndRemove(Vector2d position)
    {
        if(this.isGrassAt(position))
        {
            Grass grassHere = (Grass)getTreeSetAt(position).last();
            this.getTreeSetAt(position).pollLast();
            return grassHere;
        }
        return null;
    }

    private TreeSet<IMapElement> getTreeSetAt(Vector2d position)
    {
        return this.board.get(position.x).get(position.y);
    }

    private ArrayList<Animal> getAnimalsAt(Vector2d position)
    {
        ArrayList<Animal> animals = new ArrayList<>();
        for(IMapElement element: this.getTreeSetAt(position))
        {
            if(element instanceof Animal)
                animals.add((Animal)element);
        }
        return animals;
    }

    void printTreeAt(Vector2d position)
    {
        TreeSet<IMapElement> tree = this.getTreeSetAt(position);
        System.out.println(tree);
        for(IMapElement cos: tree)
            System.out.println(cos);
    }
}
