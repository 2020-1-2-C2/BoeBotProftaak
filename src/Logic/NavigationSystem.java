package Logic;

import java.util.ArrayList;

//TODO: Rename class and functions to more accurately represent its functionality.
//TODO: Add more and better documentation.

/**
 * Class used to generate <a href="{@docRoot}/Logic/NavigationSystem.html">Route.java</a> instances, used by the BoeBot to drive.
 * The method <code>followRoute()</code> in <a href="{@docRoot}/Logic/DriveSystem.html">DriveSystem.java</a> takes in a <code>Route</code> as a parameter, and is used for the navigation of the robot.
 * @author Berend de Groot, Casper Lous
 * @version 1.1
 * @see DriveSystem#followRoute(Route)
 */
public class NavigationSystem {

    /**
     * These should be the coordinates of the BoeBot's position when the instructions are received.
     * However, these attributes are assigned in its constructor and could be anything we want it to be.
     */
    private int currentCoordX;
    private int currentCoordY;

    private int goalCoordX;
    private int goalCoordY;

    private ArrayList<Integer> routeToNavigate;

    private int direction;

    private static final int LEFT = -1;
    private static final int FORWARD = 0;
    private static final int RIGHT = 1;
    private static final int BACKWARDS = 3;

    /**
     * Constructor which only takes the destination's coordinates. The <code>currentCoord</code>s will be set to 0.
     * @param goalCoordX X-coordinate of destination. Think about this like your car's GPS.
     * @param goalCoordY Y-coordinate of destination. Think about this like your car's GPS.
     */
    public NavigationSystem(int goalCoordX, int goalCoordY){
        this.currentCoordX = 0;
        this.currentCoordY = 0;

        this.goalCoordX = goalCoordX;
        this.goalCoordY = goalCoordY;
        this.direction = FORWARD;
        this.routeToNavigate = new ArrayList<>();
        this.faceForward();
    }

    /**
     * Constructor taking in all information related to the BoeBot's position, and then initializing them.
     * @param currentCoordX X-coordinate of the original position, used for calculating the distance to the final destination.
     * @param currentCoordY Y-coordinate of the original position, used for calculating the distance to the final destination.
     * @param goalCoordX X-coordinate of destination. Think about this like your car's GPS.
     * @param goalCoordY Y-coordinate of destination. Think about this like your car's GPS.
     */
    public NavigationSystem(int currentCoordX, int currentCoordY, int goalCoordX, int goalCoordY){
        this(goalCoordX, goalCoordY);
        this.currentCoordX = currentCoordX;
        this.currentCoordY = currentCoordY;
    }

    /**
     * Calculates the distance and adds instructions to this <code>routeToNavigate</code> ArrayList accordingly.
     */
    private void calculateRoute (){
        int differenceX =  this.goalCoordX - this.currentCoordX;
        int differenceY =  this.goalCoordY - this.currentCoordY;

        if (differenceX > 0){
            if (this.direction != RIGHT){
                this.faceForward();
                this.routeToNavigate.add(RIGHT);
                this.direction = RIGHT;
            }
            for (int i = 0; i < differenceX; i++){
                this.routeToNavigate.add(FORWARD);
            }
        } else if (differenceX < 0){
            if (this.direction != LEFT){
                this.faceForward();
                this.routeToNavigate.add(LEFT);
                this.direction = LEFT;
            }
            for (int i = 0; i > differenceX; i--){
                this.routeToNavigate.add(FORWARD);
            }
        }

        if (differenceY > 0){
            if (this.direction != FORWARD){
                this.faceForward();
            }
            for (int i = 0; i < differenceY; i++){
                this.routeToNavigate.add(FORWARD);
            }
        } else if (differenceY < 0){
            //TODO: Add backwards functionality.
            /* NOTE: DON'T DO THIS!!!!
            TODO: This was done to ensure all emergency functionality works; the UltraSonicSensor is on the front of the bot!!!

            TODO: TEST! This should be added.
             */
            if (this.direction == BACKWARDS){
                this.faceForward();
                this.routeToNavigate.add(LEFT);
                this.routeToNavigate.add(LEFT);
            }
            for (int i = 0; i > differenceY; i--){
                this.routeToNavigate.add(FORWARD);
            }
        }
    }

    /**
     * Turns the BoeBot so that it has the same orientation as when it was turned on.
     */
    private void faceForward(){
        if (this.direction == BACKWARDS){
            this.routeToNavigate.add(RIGHT);
            this.routeToNavigate.add(RIGHT);
        } else if (this.direction == RIGHT){
            this.routeToNavigate.add(LEFT);
        } else if (this.direction == LEFT){
            this.routeToNavigate.add(RIGHT);
        }
    }

    /**
     * Calculates the route of this instance and then returns a new <code>Route</code> based on <code>this.routeToNavigate</code>.
     * @return <code>Route</code> containing instructions for the BoeBot, so that it can navigate on white paper with black tape.
     * @see Route#Route(ArrayList)
     */
    public Route getRoute(){
        this.calculateRoute();
        return new Route(this.routeToNavigate);
    }

}