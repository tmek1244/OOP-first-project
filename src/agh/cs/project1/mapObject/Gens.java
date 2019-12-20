package agh.cs.project1.mapObject;

import java.util.Arrays;
import java.util.Random;


class Gens {
    private static int NUMBER_OF_GENS = 24;
    private static int NUMBER_OF_DIFF_GENS = 8;
    private int[] gens;

    Gens()
    {
        this.gens = getRandomGens();
    }

    Gens(Gens firstParent, Gens secondParent)
    {
        this.gens = createGensForKid(firstParent, secondParent);
    }

    int getAngleInInt()
    {
        int whichMove = new Random().nextInt(NUMBER_OF_GENS+ NUMBER_OF_DIFF_GENS);
        if(whichMove < NUMBER_OF_DIFF_GENS)
            return whichMove;
        else
            return this.gens[whichMove - NUMBER_OF_DIFF_GENS];
    }

    private static int [] getRandomGens()
    {
        int[] gens = new int[NUMBER_OF_GENS];
        for(int i = 0; i < NUMBER_OF_GENS; i++)
        {
            gens[i] = new Random().nextInt(NUMBER_OF_DIFF_GENS);
        }
        Arrays.sort(gens);
        return gens;
    }

    private static int[] createGensForKid(Gens firstParent, Gens secondParent)
    {
        int firstCut = new Random().nextInt(NUMBER_OF_GENS);
        int secondCut = new Random().nextInt(NUMBER_OF_GENS - firstCut) + firstCut;
        int[] gensForKid = Arrays.copyOf(secondParent.gens, NUMBER_OF_GENS);
        if (secondCut - firstCut >= 0)
            System.arraycopy(firstParent.gens, firstCut, gensForKid, firstCut, secondCut - firstCut);

        Arrays.sort(gensForKid);

        return gensForKid;
    }
}
