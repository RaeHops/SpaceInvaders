import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Bullet {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public Rectangle rec;
    public boolean isCrashing;

    public Bullet(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx = 1;
        dy = 0;
        width = 60;
        height = 60;
        isAlive = false;
        isCrashing = false;
        rec = new Rectangle(xpos, ypos, width, height);
    }
    public void move() {

        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle(xpos, ypos, width, height);
    }
    public void Collision(){
        isAlive = false;
        isCrashing = true;

    }

}
