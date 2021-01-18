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
    public static final int DESTINATION = 3;
    public static final int NONE = 2;

    private ArrayList<Integer> route;
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
        if (this.route.size() < 1) {
            return null;
        } else if(this.index >= this.route.size()) {
            return NONE;
        } else {
            int result = this.route.get(index);
            this.index++;
            return result;
        }
    }

    /**
     * Reverses the route and continue from the end of the previous route.
     */
    public void reverse() {
        for (int i = 0; i < this.route.size(); i++) {
            if (this.route.get(i) == LEFT) {
                this.route.set(i, RIGHT);
            } else if (this.route.get(i) == RIGHT) {
                this.route.set(i, LEFT);
            }
        }
        Collections.reverse(this.route);
        this.index = 0;
    }

    public ArrayList<Integer> getRoute() {
        return this.route;
    }
}
