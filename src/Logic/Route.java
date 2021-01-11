package Logic;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class which can hold the values for a route to be followed
 *
 * @author Berend de Groot, Martijn de Kam, Meindert Kampe, Lars Hoendervangers
 */
public class Route {
    static final int LEFT = -1;
    static final int FORWARD = 0;
    static final int RIGHT = 1;
    static final int DESTINATION = 3;
    static final int NONE = 2;

    private ArrayList<Integer> route;
    private int index;

    /**
     * Constructor for the <code>Route</code> class.
     * @param route ArrayList of directions as integers.
     */
    Route(ArrayList<Integer> route) {
        this.route = route;
        this.index = 0;
    }

    /**
     * @return Returns the next direction as an integer.
     */
    Integer nextDirection() {
        if (route.size() < 1) {
            return null;
        } else if(index >= this.route.size()) {
            return NONE;
        } else {
            int result = this.route.get(index);
            this.index++;
            return result;
        }
    }


    /**
     * Reverse the route and continue from the end of the previous route.
     */
    void reverse() {
        for (int i = 0; i < this.route.size(); i++) {
            if (route.get(i) == LEFT) {
                this.route.set(i, RIGHT);
            } else if (this.route.get(i) == RIGHT) {
                this.route.set(i,LEFT);
            }
        }
        Collections.reverse(this.route);
        this.index = 0;
    }

    ArrayList<Integer> getRoute() {
        return this.route;
    }
}
