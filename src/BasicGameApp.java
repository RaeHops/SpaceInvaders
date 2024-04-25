//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image background;
    public Image astroPic;

    public Image alienPic;
    public Image shipPic;
    public Image bulletPic;
    public Spaceship ship;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Astronaut astro;

    public SpaceInvaders[] Aliens;
    public Bullet[] Bullets;

    int BulletCounter = 0;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() {

        Aliens = new SpaceInvaders[10];
        for (int z = 0; z < Aliens.length; z++) {
            Aliens[z] = new SpaceInvaders(z * 100, 50);
//
        }

        ship = new Spaceship(450, 500);

        Bullets = new Bullet[100];
        for (int z = 0; z < Bullets.length; z++) {
            Bullets[z] = new Bullet(ship.xpos, ship.ypos);
//
        }


        Bullets[0].isAlive = true;
        if (Bullets[0].isAlive == true && Bullets[0].rec.intersects(ship.rec) == false) {
            for (int x = 0; x < Bullets.length; x++) {
                if (!Bullets[x].isAlive) {
                    Bullets[x].isAlive = true;
                    Bullets[x].rec.setLocation(ship.xpos, ship.ypos);
                    break;
                }
            }
        }


        setUpGraphics();

        //variable and objects
        //create (construct) the objects needed for the game and load up
        astroPic = Toolkit.getDefaultToolkit().getImage("astronaut.png"); //load the picture
        astro = new Astronaut(10, 100);

        alienPic = Toolkit.getDefaultToolkit().getImage("Alien.jpg"); //load the picture
        shipPic = Toolkit.getDefaultToolkit().getImage("Ship.png"); //load the picture
        bulletPic = Toolkit.getDefaultToolkit().getImage("Bullet.png");
        background = Toolkit.getDefaultToolkit().getImage("SpaceInvadersBackground.jpg"); //load the picture


    }// BasicGameApp()


//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {

        //for the moment we will loop things forever.
        while (true) {

            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(20); // sleep for 10 ms
        }
    }


    public void moveThings() {
        //calls the move( ) code in the objects
        astro.move();
        ship.WrapAndControl();
        for (int x = 0; x < Aliens.length; x++) {
            Aliens[x].bounce();
        }
        for (int x = 0; x < Bullets.length; x++) {
           if (Bullets[x].isAlive == true){
               Bullets[x].move();
           }
        }
        for (int a = 0; a < Bullets.length; a++) {
            for (int x = 0; x < Aliens.length; x++) {
                if (Bullets[a].rec.intersects(Aliens[x].rec) && !Bullets[a].isCrashing && !Aliens[x].isCrashing) {
                   // Bullets[a].isCrashing = true;
                  //  Aliens[x].isCrashing = true;
                        System.out.println("Crash");
                        Bullets[x].Collision();
                        Aliens[x].Collision();

                }
            }
        }

    }

    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);
        canvas.addKeyListener(this);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw the image of the astronaut
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);

        g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
        g.drawImage(shipPic, ship.xpos, ship.ypos, ship.width, ship.height, null);

        for (int a = 0; a < Aliens.length; a++) {
            if (Aliens[a].isAlive == true) {
                g.drawImage(alienPic, Aliens[a].xpos, Aliens[a].ypos, Aliens[a].width, Aliens[a].height, null);
            }
        }
        for (int a = 0; a < Bullets.length; a++) {
            if (Bullets[a].isAlive == true) {
                g.drawImage(bulletPic, Bullets[a].xpos, Bullets[a].ypos, Bullets[a].width, Bullets[a].height, null);
            }
        }
        g.dispose();

        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == 87) {
            System.out.println("Going Up");
//			astro.dx = 0;
//			astro.dy = -5;
            ship.isNorth = true;
        }
        if (e.getKeyCode() == 65) {
            System.out.println("Going Left");
//			astro.dx = -5;
//			astro.dy = 0;
            ship.isEast = true;
        }
        if (e.getKeyCode() == 83) {
            System.out.println("Going Down");
//			astro.dx = 0;
//			astro.dy = 5;
            ship.isSouth = true;
        }
        if (e.getKeyCode() == 68) {
            System.out.println("Going Right");
//			astro.dx = 5;
//			astro.dy = 0;
            ship.isWest = true;
        }
        if (e.getKeyCode() == 32) {
            System.out.println("Bullet Spawned");
            BulletCounter = BulletCounter + 1;
            if(BulletCounter == Bullets.length){
                BulletCounter = 0;
            }
            Bullets[BulletCounter].isAlive = true;
            Bullets[BulletCounter].xpos = ship.xpos;
            Bullets[BulletCounter].ypos = ship.ypos;
            Bullets[BulletCounter].rec = new Rectangle(Bullets[BulletCounter].xpos,Bullets[BulletCounter].ypos, Bullets[BulletCounter].width, Bullets[BulletCounter].height);
//            if (Bullets[1].isAlive == true) {
//                for (int x = 0; x < Bullets.length; x++) {
//                    if (!Bullets[x].isAlive) {
//                        Bullets[x].isAlive = true;
//                        Bullets[x].rec.setLocation(ship.xpos, ship.ypos);
//
////
//                    }
//                }
//            }


        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 87) {
            System.out.println("Going Up");
            ship.isNorth = false;
        }
        if (e.getKeyCode() == 65) {
            System.out.println("Going left");
            ship.isEast = false;
        }
        if (e.getKeyCode() == 83) {
            System.out.println("Going down");
            ship.isSouth = false;
        }
        if (e.getKeyCode() == 68) {
            System.out.println("Going right");
            ship.isWest = false;
        }

    }

}