package agh.cs.project1.settings;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class LoadSettings {
    public static int width = 50, height = 30, startEnergy = 100,
            moveEnergy = 1, plantEnergy = 30, scale = 30, animalsAtBeginning = 5, periodInterval = 10;
    public static double jungleRatio = 0.4;
    public static void load()
    {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("src/parameters.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            width =(int) (long) jsonObject.get("width");
            height = (int) (long) jsonObject.get("height");
            startEnergy = (int) (long) jsonObject.get("startEnergy");
            moveEnergy = (int) (long) jsonObject.get("moveEnergy");
            plantEnergy = (int) (long) jsonObject.get("plantEnergy");
            scale = (int) (long) jsonObject.get("scale");
            animalsAtBeginning = (int) (long) jsonObject.get("animalsAtBeginning");
            periodInterval = (int) (long) jsonObject.get("periodInterval");
            jungleRatio = (double) jsonObject.get("jungleRatio");
        } catch (IOException | ParseException e) {
            System.out.println("Exception caught during loading settings.");
            e.printStackTrace();
        }
    }
}
