package Logic;

import java.util.ArrayList;
import java.util.Collections;

public class Route {
    public static final int LEFT = -1;
    public static final int FORWARD = 0;
    public static final int RIGHT = 1;
    public static final int NONE = 2;

    private ArrayList<Integer> route;
    private int index;

    public Route(ArrayList<Integer> route) {
        this.route = route;
        this.index = 0;
    }

    public Integer nextDirection() {
        if (route.size() < 1) {
            return null;
        } else if(index >= route.size()) {
            return NONE;
        } else {
            int result = route.get(index);
            this.index++;
            return result;
        }
    }


    /**
     * reverse the route and continue from the start of the route.
     */
    public void reverse() {
        for (int i = 0; i < route.size(); i++) {
            if (route.get(i) == LEFT) {
                route.set(i, RIGHT);
            } else if (route.get(i) == RIGHT) {
                route.set(i,LEFT);
            }
        }
        Collections.reverse(route);
        index = 0;
    }
}
