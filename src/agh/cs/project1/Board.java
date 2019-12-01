package agh.cs.project1;


import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Board extends JPanel {

    private Image grass;
    private Image sheep;
    private int mapSizeX;
    private int mapSizeY;
    private int jungleSizeX;
    private int jungleSizeY;
    private WorldMap map;
    private MapRepresentation mapBoard;

    private Timer timer;
    private final int INITIAL_DELAY = 0;
    private final int PERIOD_INTERVAL = 100;


    private final int scale = 30;

    public Board() {
        this.mapSizeX = 50;
        this.mapSizeY = 30;
        this.jungleSizeX = 10;
        this.jungleSizeY = 10;


        this.map = new WorldMap(this.mapSizeX, this.mapSizeY, this.jungleSizeX, this.jungleSizeY);
        this.map.place(new Animal(this.map, new Vector2d(20,10)));
        this.map.place(new Animal(this.map, new Vector2d(20,10)));
        this.map.place(new Animal(this.map, new Vector2d(20,10)));
        this.map.place(new Animal(this.map, new Vector2d(20,10)));
        this.mapBoard = this.map.getMapRepresentation();
        initBoard();
    }

    private void initBoard() {

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(this.mapSizeX * this.scale, this.mapSizeY * this.scale));
        loadImage();

        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.map.nextDay();
//        drawAllGrass(g);
        drawMap(g);
    }

    private class ScheduleTask extends TimerTask
    {

        @Override
        public void run() {
            repaint();
        }
    }
}
