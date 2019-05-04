package breakout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import java.util.Random;

public class MapGenerator {

    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public int randomnum;
    public Color v = Color.blue;

    public MapGenerator(int row, int col) {

        map = new int[row][col];

        for(int i =0; i<map.length; i ++) {

            for(int j=0; j < map[0].length; j++) {

                map[i][j] = 1;
            }
        }
        brickWidth = 540/ col;
        brickHeight = 150/ row;

        colorGenerator();
    }


    public void colorGenerator(){
        randomnum = (int) (Math.random() * 10) + 1 ;

        switch(randomnum){
            case 1 : v = Color.CYAN;
                break;
            case 2 : v = Color.red;
                break;
            case 3 : v = Color.magenta;
                break;
            case 4 : v = Color.green;
                break;
            case 5 : v = Color.YELLOW;
                break;
            case 6 : v = Color.orange;
                break;
            case 7 : v = Color.PINK;
                break;
            case 8 : v = Color.blue;
                break;
            case 9 : v = Color.white;
                break;


        }
    }




    public void draw(Graphics2D g) {


        for(int i =0; i < map.length; i++) {

            for(int j =0; j < map[0].length; j++) {

                if(map[i][j] > 0) {

                    g.setColor(v);

                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                }
            }
        }
    }

    public void setbrickValue(int value, int row, int col) {
        map[row][col]= value;
    }

}

