package Logic;

import java.util.ArrayList;
import java.util.Collections;


//TODO: Check access-modifiers.

public class Route {
    public static final int LEFT = -1;
    public static final int FORWARD = 0;
    public static final int RIGHT = 1;
    public static final int NONE = 2;
    public static final int BACKWARDS = 3;

    private ArrayList<Integer> route;
    private int index;

    /**
     * Constructor for the <code>Route.java</code> class.
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
     * Reverse the route and continue from the end of the previous route. //TODO: Add backwards functionality.
     */
    public void reverse() {
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

    public ArrayList<Integer> getRoute() {
        return this.route;
    }
}
