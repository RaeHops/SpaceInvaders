import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Spaceship {

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
    public boolean isNorth;
    public boolean isSouth;
    public boolean isEast;
    public boolean isWest;

    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Spaceship(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx =1;
        dy =0;
        width = 60;
        height = 60;
        isAlive = true;
        isCrashing = false;
        rec = new Rectangle(xpos, ypos, width, height);
        isNorth = false;
        isSouth = false;
        isEast = false;
        isWest = false;

    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle(xpos, ypos, width, height);

    }
    public void WrapAndControl(){
        if(xpos < 0){
            xpos = 1000-width;

        }
        if(xpos > 1000-width){
            xpos = 0;

        }
        if(ypos < 0){
            ypos = 700-height;

        }
        if(ypos > 700-height){
            ypos = 0;

        }
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle(xpos, ypos, width, height);

        if(isNorth == false && isSouth == false){
            dy = 0;
        } else if(isNorth){
            dy = -5;
        } else{
            dy = 5;
        }

        if(isEast == false && isWest == false){
            dx = 0;
        } else if(isEast){
            dx = -5;
        } else{
            dx = 5;
        }


    }
}







