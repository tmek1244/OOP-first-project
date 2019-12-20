package agh.cs.project1.visualization;

import agh.cs.project1.mapRepresentation.Vector2d;
import agh.cs.project1.mapRepresentation.World;
import agh.cs.project1.mapObject.Animal;
import agh.cs.project1.mapObject.Grass;
import agh.cs.project1.mapObject.IMapElement;
import agh.cs.project1.settings.LoadSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Board extends JPanel {

    private Image grass;
    private Image sheep;
    private int mapSizeX;
    private int mapSizeY;
    private World map;

    private final int PERIOD_INTERVAL = LoadSettings.periodInterval;


    private final int scale = LoadSettings.scale;

    Board() {
        this.mapSizeX = LoadSettings.width;
        this.mapSizeY = LoadSettings.height;
        int jungleSizeX = (int) (this.mapSizeX * LoadSettings.jungleRatio);
        int jungleSizeY = (int) (this.mapSizeY * LoadSettings.jungleRatio);


        this.map = new World(this.mapSizeX, this.mapSizeY, jungleSizeX, jungleSizeY);
        for(int i = 0; i < LoadSettings.animalsAtBeginning; i++)
            this.map.place(new Animal(this.map, new Vector2d(this.mapSizeX/2,this.mapSizeY/2)));

        initBoard();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.map.nextDay();
        drawMap(g);
    }

    private void initBoard() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(this.mapSizeX * this.scale, this.mapSizeY * this.scale));
        loadImage();

        Timer timer = new Timer();
        int INITIAL_DELAY = 0;
        timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
    }

    private void drawMap(Graphics g)
    {
        for(int i = 0; i < this.mapSizeX; i++){
            for(int j = 0; j < this.mapSizeY; j++)
            {
                IMapElement objectToDraw = this.map.getElementOnPosition(new Vector2d(i, j));

                if(objectToDraw != null)
                {
                   if(objectToDraw instanceof Animal) {
//                       System.out.println("board: " + objectToDraw);
                       g.drawImage(this.sheep, this.scale * i, this.scale * j, this);
                   }
                   else if(objectToDraw instanceof Grass)
                       g.drawImage(this.grass, this.scale*i, this.scale*j, this);
                }
            }
        }
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/images/grass.png");
        this.grass = ii.getImage().getScaledInstance(this.scale, this.scale, Image.SCALE_DEFAULT);
        ii = new ImageIcon("src/images/sheep.png");
        this.sheep = ii.getImage().getScaledInstance(this.scale, this.scale, Image.SCALE_DEFAULT);
    }

    private class ScheduleTask extends TimerTask
    {
        @Override
        public void run() {
            repaint();
        }
    }
}
