package logic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class which can hold the values for a route to be followed
 *
 * @author Berend de Groot, Martijn de Kam, Meindert Kampe, Lars Hoendervangers
 */
public class Route {
    public static final int LEFT = -1;
    public static final int FORWARD = 0;
    public static final int RIGHT = 1;
    public static final int ROTATE = -2;
    public static final int DESTINATION = 3;
    public static final int NONE = 2;

    private ArrayList<Integer> route;
    boolean isReversed = false;
    private int index;

    /**
     * Constructor for the <code>Route</code> class.
     * @param route ArrayList of directions as integers.
     */
    public Route(ArrayList<Integer> route) {
        this.route = route;
        this.index = 0;
    }

    /**
     * @return Returns the next direction as an integer.
     */
    public Integer nextDirection() {
        // a reversed route should not execute the final step
        int correction = 0;
        if (this.isReversed) {
            correction = 1;
        }
        if (this.route.size() < 1) {
            return null;
        } else if(this.index >= this.route.size() - 1) {
            return NONE;
        } else {
            int result = this.route.get(index);
            this.index++;
            return result;
        }
    }

    /**
     * Method that returns the next direction without increasing the index
     * @return
     */
    public Integer testNextDirection() {
        if (this.route.size() < 1) {
            return null;
        } else if(this.index >= this.route.size()) {
            return NONE;
        } else {
            return this.route.get(index);
        }
    }

    /**
     * Reverses the route and continue from the end of the previous route.
     */
    public void reverse() {
        ArrayList<Integer> reversedRoute = new ArrayList<>();
        // reverses the route
        // LEFT and RIGHT need to be swapped with eachother
        // DESTINATIONs should not be taken into the new list
        for (int i = this.route.size() - 1; i >= 0; i--) {
            int direction = this.route.get(i);
            if (direction == LEFT) {
                reversedRoute.add(RIGHT);
            } else if (direction == RIGHT) {
                reversedRoute.add(LEFT);
            } else if (direction != DESTINATION) {
                reversedRoute.add(direction);
            }
        }
        this.route = reversedRoute;
        this.index = 0;
        this.isReversed = true;
    }

    /**
     * @return the current route.
     */
    public ArrayList<Integer> getRoute() {
        return this.route;
    }
}
