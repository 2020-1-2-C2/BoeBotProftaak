package logic;

import java.util.ArrayList;

/**
 * Class used to generate <a href="{@docRoot}/logic/NavigationSystem.html">Route.java</a> instances, used by the BoeBot to drive.
 * The method <code>followRoute()</code> in <a href="{@docRoot}/logic/DriveSystem.html">DriveSystem.java</a> takes
 * in a <code>Route</code> as a parameter, and is used for the navigation of the robot.
 * @author Berend de Groot
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
    private ArrayList<Integer> destinationCoordsList;

    private static final int NORTH = 9;
    private static final int SOUTH = 8;
    private static final int EAST = 7;
    private static final int WEST = 6;
    private static final int NOORIENTATION = 10;
    private int orientation = NOORIENTATION;

    private ArrayList<Integer> routeToNavigate;

    /**
     * Constructor which only takes the destination's coordinates. The <code>currentCoord</code>s will be set to 0.
     * @param destinationCoordsList ArrayList with the coordinates of the destinations
     */
    public NavigationSystem(ArrayList<Integer> destinationCoordsList) {
        this(0, 0, destinationCoordsList);
    }

    /**
     * Constructor for a NavigationSystem object.
     * Takes the current coordinates of X and Y as the starting position.
     * Takes a list of destinations, in the format of a two digit integer in the format of XY as each element.
     *
     * @param currentCoordX start coord X
     * @param currentCoordY start coord Y
     * @param destinationsCoordsXAndY all destination coords
     */
    public NavigationSystem(int currentCoordX, int currentCoordY, ArrayList<Integer> destinationsCoordsXAndY) {
        System.out.println(destinationsCoordsXAndY);
        this.destinationCoordsList = destinationsCoordsXAndY;
        this.routeToNavigate = new ArrayList<>();
        this.currentCoordX = currentCoordX;
        this.currentCoordY = currentCoordY;
        // get the first digit of the first index of destinationCoordsXAndY
        this.goalCoordX = getFirstDigitOfATwoDigitNumber(destinationsCoordsXAndY.get(0));
        // get the second digit of the first index of destinationCoordsXAndY
        this.goalCoordY = getSecondDigitOfATwoDigitNumber(destinationsCoordsXAndY.get(0));
        determineStartingOrientation();
        System.out.println("NavigationSystem: " + "" + currentCoordX + "" + currentCoordY + "" + this.goalCoordX + "" + this.goalCoordY);
    }

    /**
     * Calculates the distance and adds instructions to this <code>routeToNavigate</code> ArrayList accordingly.
     * Only supports starting from the edge, doesn't support moving backwards
     */
    private ArrayList<Integer> calculateRouteBetweenTwoPoints(int startingCoordX, int startingCoordY, int goalCoordX, int goalCoordY) {
        int differenceX =  goalCoordX - startingCoordX;
        int differenceY =  goalCoordY - startingCoordY;
        System.out.println("Calculating route");
        System.out.println("DifferenceX = " + differenceX + " and differenceY = " + differenceY);

        ArrayList<Integer> route = new ArrayList<>();

        if (differenceY > 0) {
            for (int i = 0; i < differenceY; i++) {
                route.add(Route.FORWARD);
            }
        } else if (differenceY < 0) {
            // implement backwards implementation
        }

        if (differenceX > 0) {
            route.add(Route.RIGHT);
            if (differenceX > 1) {
                for (int i = 1; i < differenceX; i++) {
                    route.add(Route.FORWARD);
                }
            }
        } else if (differenceX < 0) {
            route.add(Route.LEFT);
            if (differenceX < -1) {
                for (int i = -1; i > differenceX; i--) {
                    route.add(Route.FORWARD);
                }
            }
        }

        route.add(Route.DESTINATION);
        System.out.println(route);
        determineOrientation(route);
        return route;
    }

    /**
     * Calculates the distance and adds instructions to this <code>routeToNavigate</code> ArrayList accordingly.
     * Only supports starting from the edge, doesn't support moving backwards
     */
    private ArrayList<Integer> calculateRouteBetweenTwoPointsWithOrientation(int startingCoordX, int startingCoordY, int goalCoordX, int goalCoordY) {
        // if no orientation exists then it's the very start and the simple method of calculating a route can be used
        if (this.orientation == NOORIENTATION) {
            return this.calculateRouteBetweenTwoPoints(startingCoordX, startingCoordY, goalCoordX, goalCoordY);
        }

        int differenceX =  goalCoordX - startingCoordX;
        int differenceY =  goalCoordY - startingCoordY;

        ArrayList<Integer> route = new ArrayList<>();

        // The bot is moving in the y direction and maybe in the x direction
        if (differenceY != 0) {
            // rotate appropriatly
            // also adjust orientation with a rotation
            if ((this.orientation == NORTH && differenceY < 0) || (this.orientation == SOUTH && differenceY > 0)) {
                route.add(Route.ROTATE);
                if (this.orientation == NORTH) {
                    this.orientation = SOUTH;
                } else {
                    this.orientation = NORTH;
                }
            } else if ((orientation == WEST && differenceY < 0) || (orientation == EAST && differenceY > 0)) {
                route.add(Route.LEFT);
                if (this.orientation == WEST) {
                    this.orientation = SOUTH;
                } else {
                    this.orientation = NORTH;
                }
            } else if ((orientation == EAST && differenceY < 0) || (orientation == WEST && differenceY > 0)) {
                route.add(Route.RIGHT);
                if (this.orientation == EAST) {
                    this.orientation = SOUTH;
                } else {
                    this.orientation = NORTH;
                }
            } else {
                route.add(Route.FORWARD);
            }

            // move forward the amount left necessary in the y direction
            if (differenceY > 1) {
                for (int i = 1; i < differenceY; i++) {
                    route.add(Route.FORWARD);
                }
            } else if (differenceY < -1) {
                for (int i = differenceY + 1; i < 0; i++) {
                    route.add(Route.FORWARD);
                }
            }

            // Now go to the necessary X position
            // this is after moving in the y direction, so orientation should either be NORTH or SOUTH
            if ((this.orientation == NORTH && differenceX < 0) || (this.orientation == SOUTH && differenceX > 0)) {
                route.add(Route.LEFT);
                if (this.orientation == NORTH) {
                    this.orientation = WEST;
                } else {
                    this.orientation = EAST;
                }
            } else if ((this.orientation == NORTH && differenceX > 0) || (this.orientation == SOUTH && differenceX < 0)) {
                route.add(Route.RIGHT);
                if (this.orientation == NORTH) {
                    this.orientation = EAST;
                } else {
                    this.orientation = WEST;
                }
            }

            // move forward the amount left necessary in the x direction
            if (differenceX > 1) {
                for (int i = 1; i < differenceX; i++) {
                    route.add(Route.FORWARD);
                }
            } else if (differenceX < -1) {
                for (int i = differenceX + 1; i < 0; i++) {
                    route.add(Route.FORWARD);
                }
            }
            // if the bot is moving in only the x direction
        } else if (differenceX != 0 && differenceY == 0) {
            if ((this.orientation == NORTH && differenceX < 0) || (this.orientation == SOUTH && differenceX > 0)) {
                route.add(Route.LEFT);
                if (this.orientation == NORTH) {
                    this.orientation = WEST;
                } else {
                    this.orientation = EAST;
                }
            } else if ((this.orientation == NORTH && differenceX > 0) || (this.orientation == SOUTH && differenceX < 0)) {
                route.add(Route.RIGHT);
                if (this.orientation == NORTH) {
                    this.orientation = EAST;
                } else {
                    this.orientation = WEST;
                }
            } else if ((orientation == EAST && differenceX < 0) || (orientation == WEST && differenceX > 0)) {
                route.add(Route.ROTATE);
                if (this.orientation == EAST) {
                    this.orientation = WEST;
                } else {
                    this.orientation = EAST;
                }
            } else {
                route.add(Route.FORWARD);
            }

            // move forward the amount left necessary in the x direction
            if (differenceX > 1) {
                for (int i = 1; i < differenceX; i++) {
                    route.add(Route.FORWARD);
                }
            } else if (differenceX < -1) {
                for (int i = differenceX + 1; i < 0; i++) {
                    route.add(Route.FORWARD);
                }
            }
        }

        route.add(Route.DESTINATION);
        System.out.println(route);
        return route;
    }


    /**
     * Adds a given list which contains a route with directions and at the end a destination and adds it to the private attribute routeToNavigate.
     * @param routeToAdd
     */
    private void addSingleRouteToTotal(ArrayList<Integer> routeToAdd) {
        this.routeToNavigate.addAll(routeToAdd);
    }

    /**
     * Calculates the route of this instance and then returns a new <code>Route</code> based on <code>this.routeToNavigate</code>.
     * @return <code>Route</code> containing instructions for the BoeBot, so that it can navigate on white paper with black tape.
     * @see Route#Route(ArrayList)
     */
    public Route calculateNewSimpleRoute() {
        addSingleRouteToTotal(calculateRouteBetweenTwoPoints(this.currentCoordX, this.currentCoordY, this.goalCoordX, this.goalCoordY));
        return new Route(this.routeToNavigate);
    }

    public Route calculateNewAdvancedRoute() {
        System.out.println("Calculating an advanced route");
        System.out.println("Amount of destinations: " + this.destinationCoordsList.size());
        addSingleRouteToTotal(calculateRouteBetweenTwoPoints(this.currentCoordX, this.currentCoordY,
                getFirstDigitOfATwoDigitNumber(this.destinationCoordsList.get(0)),
                getSecondDigitOfATwoDigitNumber(this.destinationCoordsList.get(0))));
        for (int i = 0; i < this.destinationCoordsList.size() - 1; i++) {
            System.out.println("Calculating destination " + i);
            ArrayList<Integer> newRoute = calculateRouteBetweenTwoPointsWithOrientation(getFirstDigitOfATwoDigitNumber(this.destinationCoordsList.get(i)),
                    getSecondDigitOfATwoDigitNumber(this.destinationCoordsList.get(i)),
                    getFirstDigitOfATwoDigitNumber(this.destinationCoordsList.get(i + 1)),
                    getSecondDigitOfATwoDigitNumber(this.destinationCoordsList.get(i + 1)));
            addSingleRouteToTotal(newRoute);
        }
        return new Route(this.routeToNavigate);
    }

    public void printRoute(){
        System.out.print("RouteToNavigate: ");
        for (Integer i : this.routeToNavigate){
            System.out.print(i + ", ");
        }
        System.out.println("");
    }

    /**
     * Simple method for eliminating the first digit out of a two digit number
     * @param number two digit number
     * @return first digit of the number
     */
    private int getFirstDigitOfATwoDigitNumber(int number) {
        if (number < 10) {
            return 0;
        }
        return number / 10;
    }

    /**
     * Simple method for eliminating the second digit out of a two digit number
     * @param number two digit number
     * @return second digit of the number
     */
    private int getSecondDigitOfATwoDigitNumber(int number) {
        return number % 10;
    }

    private void determineStartingOrientation() {
        if (this.currentCoordY == 0) {
            this.orientation = NORTH;
        } else if (this.currentCoordX == 0) {
            this.orientation = EAST;
        } else {
            // standard if unknown is NORTH
            this.orientation = NORTH;
        }
        // Facing SOUTH or WEST at the start requires to know the dimensions of the roster or to include an orientation in the bluetooth message
    }

    private void determineOrientation(ArrayList<Integer> addedRoute) {
        for (Integer i : addedRoute) {
            if (this.orientation == NORTH) {
                if (i == Route.LEFT) {
                    this.orientation = WEST;
                } else if (i == Route.RIGHT) {
                    this.orientation = EAST;
                }
            } else if (this.orientation == SOUTH) {
                if (i == Route.LEFT) {
                    this.orientation = EAST;
                } else if (i == Route.RIGHT) {
                    this.orientation = WEST;
                }
            } else if (this.orientation == EAST) {
                if (i == Route.LEFT) {
                    this.orientation = NORTH;
                } else if (i == Route.RIGHT) {
                    this.orientation = SOUTH;
                }
            } else if (this.orientation == WEST) {
                if (i == Route.LEFT) {
                    this.orientation = SOUTH;
                } else if (i == Route.RIGHT) {
                    this.orientation = NORTH;
                }
            }
        }
    }
}