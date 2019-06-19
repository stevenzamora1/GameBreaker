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

    //First Ball Details
    private int ballPosx = 120;
    private int ballPosy = 350;
    private int ballXdir= +1;
    private int ballYdir= +2;

    //Second Ball Deatils
    private int ball2Posx = 420;
    private int ball2Posy = 350;
    private int ball2Xdir= +1;
    private int ball2Ydir= +2;

    private boolean secondball = false;

    private MapGenerator map;
    private int randomnum = 1;
    private int randomnum2 = 2;
    public Color v = Color.blue;
    public int count;


    public void numGenerator() {

        randomnum = (int) (Math.random() * 10) + 3;

        randomnum2 = (int) (Math.random() * 10) + 4;
    }

    public Gameplay() {

       numGenerator();


        map = new MapGenerator(randomnum,randomnum2);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }


    //Generates a random color
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

    public void paint(Graphics g) {

        // background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //map
        map.draw((Graphics2D)g);

        // borders
        g.setColor(v);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);

        // paddle
        g.setColor(v );
        g.fillRect(playerX, 550, 100, 8);

        // the ball
        g.setColor(v);
        g.fillOval(ballPosx, ballPosy, 20, 20);


        //Will Draw the second ball if the the user asks for it to be drawn
       if(secondball == true){
           // the ball
           g.setColor(v);
           g.fillOval(ball2Posx, ball2Posy, 20, 20);

           g.setColor(v );
           g.fillRect(playerX, 550, 130, 8);

       }

       else{
           g.setColor(v);
           g.fillRect(playerX, 550, 100, 8);
       }

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

            // Message to alert the user you could play with two balls
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press 2 to have two balls" , 300, 330);
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
            g.drawString("Press enter to restart", 228, 340);

            // Message to alert the user you can play with two balls
            g.setColor(Color.blue);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press 2 for 2 balls" , 230, 400);

        }


    }


    public void actionPerformed(ActionEvent e) {

        timer.start();

        if(play) {
            count++;
            if(count == 80){
                count = 0;
                colorGenerator();
            }


            //Detects if there is a second ball too make the width of the the player higher
            if(secondball == true) {
                if(new Rectangle(ballPosx, ballPosy, 20 , 20).intersects(new Rectangle(playerX,550,130,8))) {
                    ballYdir = -ballYdir;
                }

                if(new Rectangle(ball2Posx, ball2Posy, 20 , 20).intersects(new Rectangle(playerX,550,130,8))) {
                    ball2Ydir = -ball2Ydir;
                }
            }

            else {
                if (new Rectangle(ballPosx, ballPosy, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                    ballYdir = -ballYdir;
                }

                if (new Rectangle(ball2Posx, ball2Posy, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                    ball2Ydir = -ball2Ydir;
                }
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
                        Rectangle ball2Rect = new Rectangle(ball2Posx, ball2Posy, 20, 20);
                        Rectangle brickRect = rect;


                        //Detects if the first ball hits a block
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


                        //Dectects if the second ball hits a block
                        if(ball2Rect.intersects(brickRect)) {
                            map.setbrickValue(0, i, j);
                            totalBricks--;
                            score +=5;


                            if(ball2Posx + 19 <= brickRect.x || ball2Posx + 1 >= brickRect.x + brickRect.width) {

                                ball2Xdir = -ball2Xdir;

                            }

                            else {
                                ball2Ydir =-ball2Ydir;
                            }

                            break A;

                        }


                    }
                }
            }
            //Changes the postion of ball 1
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

            //If Ball 2 is active then this changes the postion of the second ball
            ball2Posx += ball2Xdir;
            ball2Posy += ball2Ydir;
            if(ball2Posx < 0) {

                ball2Xdir = -ball2Xdir;

            }
            if(ball2Posy < 0) {
                ball2Ydir = -ball2Ydir;
            }

            if(ball2Posx > 670) {
                ball2Xdir = -ball2Xdir;
            }



        }

        repaint();


    }


    public void keyPressed(KeyEvent e) {

        //Will Move player to the right if the right key is pressed
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            }

            else {
                moveRight();
            }
        }

        //Will move player to the left if the left key is pressed
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10) {
                playerX = 10;
            }

            else {
                moveLeft();
            }
        }


        /*This method will restart the game with
        different number of blocks and different
        color blocks
        */
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {

            play = true;
            secondball = false;

            ballPosx = 120;
            ballPosy = 350;
            ballXdir = +1;
            ballYdir = +2;
            playerX = 300;
            score = 0;
            totalBricks = 36;
            numGenerator();

            map = new MapGenerator(randomnum, randomnum2);


        }

        /*This method will restart the game with
        two balls instead of one, it also will
        alter the paddle so that there is slightly bigger
        and moves slightly faster
         */
        if(e.getKeyCode() == KeyEvent.VK_2){
            play = true;
            secondball = true;

            ballPosx = 120;
            ballPosy = 350;
            ballXdir = -1;
            ballYdir = -2;

            ball2Posx = 420;
            ball2Posy = 350;
            ball2Xdir = +1;
            ball2Ydir = +2;

            playerX = 310;
            score = 0;
            totalBricks = 36;

            numGenerator();

            map = new MapGenerator(randomnum, randomnum2);
        }





    }


    private void moveLeft() {
        play = true;


        if(secondball == true){
            playerX -= 40;

        }

        else
            playerX -= 25;


    }

    private void moveRight() {
        play = true;
        if(secondball == true){
            playerX += 40;

        }

        else
            playerX += 25;

    }

    public void keyReleased(KeyEvent e) {


    }


    public void keyTyped(KeyEvent e) {


    }

}
