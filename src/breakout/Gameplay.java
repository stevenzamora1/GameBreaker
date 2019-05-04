package breakout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int totalBricks = 36;
    private Timer timer;
    private int delay = 8;
    private int score = 0;
    private int playerX = 310;
    private int ballPosx = 120;
    private int ballPosy = 350;
    private int ballXdir= -1;
    private int ballYdir= -2;
    private MapGenerator map;
    private int randomnum = 1;
    private int randomnum2 = 2;

    public void numGenerator() {

        randomnum = (int) (Math.random() * 10) + 3;

        randomnum2 = (int) (Math.random() * 10) + 4;
    }

    public Gameplay() {

       numGenerator();

       //Check to see what the numbers are for testing
        // Purposes
        System.out.print(randomnum + "second is " + randomnum2);
        map = new MapGenerator(randomnum,randomnum2);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // background

        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //map
        map.draw((Graphics2D)g);

        // borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosx, ballPosy, 20, 20);

        if(totalBricks == 0) {

            play = false;
            ballXdir = 0;
            ballYdir = 0;

            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("you won!" , 265, 300);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Score: " + score, Font.BOLD, 20);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press enter to restart", 230, 400);
        }

        if(ballPosy > 570) {

            play = false;
            ballXdir = 0;
            ballYdir = 0;



            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over", 238, 300);



            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Score: " + score, 265, 320);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press enter to restart", 228, 400);

        }
    }


    public void actionPerformed(ActionEvent e) {

        timer.start();

        if(play) {
            if(new Rectangle(ballPosx, ballPosy, 20 , 20).intersects(new Rectangle(playerX,550,100,8))) {
                ballYdir = -ballYdir;
            }

            A: for(int i =0; i <map.map.length; i++) {
                for(int j =0; j < map.map[0].length; j++) {
                    if(map.map[i][j]> 0) {
                        int brickX= j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;


                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosx, ballPosy, 20, 20);
                        Rectangle brickRect = rect;


                        if(ballRect.intersects(brickRect)) {
                            map.setbrickValue(0, i, j);
                            totalBricks--;
                            score +=5;


                            if(ballPosx + 19 <= brickRect.x || ballPosx + 1 >= brickRect.x + brickRect.width) {

                                ballXdir = -ballXdir;

                            }

                            else {
                                ballYdir =-ballYdir;
                            }

                            break A;

                        }


                    }
                }
            }

            ballPosx += ballXdir;
            ballPosy += ballYdir;
            if(ballPosx < 0) {

                ballXdir = -ballXdir;

            }
            if(ballPosy < 0) {
                ballYdir = -ballYdir;
            }

            if(ballPosx > 670) {
                ballXdir = -ballXdir;
            }



        }

        repaint();


    }


    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            }

            else {
                moveRight();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10) {
                playerX = 10;
            }

            else {
                moveLeft();
            }
        }





        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {

            play = true;
            ballPosx = 120;
            ballPosy = 350;
            ballXdir = -1;
            ballYdir = -2;
            playerX = 310;
            score = 0;
            totalBricks = 36;
            numGenerator();

            map = new MapGenerator(randomnum, randomnum2);


        }






    }


    private void moveLeft() {
        play = true;
        playerX -= 25;

    }

    private void moveRight() {
        play = true;
        playerX += 25;

    }

    public void keyReleased(KeyEvent e) {


    }


    public void keyTyped(KeyEvent e) {


    }

}
