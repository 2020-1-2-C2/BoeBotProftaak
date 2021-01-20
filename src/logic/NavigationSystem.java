package logic;

import java.util.ArrayList;

//TODO multiple stops

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

    private ArrayList<Integer> routeToNavigate;

    /**
     * Constructor which only takes the destination's coordinates. The <code>currentCoord</code>s will be set to 0.
     * @param destinationCoordsList ArrayList with the coordinates of the destinations
     */
    public NavigationSystem(ArrayList<Integer> destinationCoordsList) {
        this(0, 0, destinationCoordsList);
    }

//    /**
//     * Constructor taking in all information related to the BoeBot's position, and then initializing them.
//     * @param currentCoordX X-coordinate of the original position, used for calculating the distance to the final destination.
//     * @param currentCoordY Y-coordinate of the original position, used for calculating the distance to the final destination.
//     * @param goalCoordX X-coordinate of destination. Think about this like your car's GPS.
//     * @param goalCoordY Y-coordinate of destination. Think about this like your car's GPS.
//     */
//    public NavigationSystem(int currentCoordX, int currentCoordY, int goalCoordX, int goalCoordY) {
//        this.currentCoordX = currentCoordX;
//        this.currentCoordY = currentCoordY;
//        this.goalCoordX = goalCoordX;
//        this.goalCoordY = goalCoordY;
//        this.routeToNavigate = new ArrayList<>();
//        System.out.println("NavigationSystem: " + "" + currentCoordX + "" + currentCoordY + "" + goalCoordX + "" + goalCoordY);
//    }

    public NavigationSystem(int currentCoordX, int currentCoordY, ArrayList<Integer> destinationsCoordsXAndY) {
        System.out.println(destinationsCoordsXAndY);
        this.destinationCoordsList = destinationsCoordsXAndY;
        this.routeToNavigate = new ArrayList<>();
        this.currentCoordX = currentCoordX;
        this.currentCoordY = currentCoordY;
        // get the first digit of the first index of destinationCoordsXAndY
        this.goalCoordX = getFirstDigitOfATwoDigitNumber(destinationsCoordsXAndY.get(0));
        // get the second digit of the first index of destinationCoordsXAndY
        this.goalCoordY = getSeoncdDigitOfATwoDigitNumber(destinationsCoordsXAndY.get(0));
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

        //TODO this is temporarily untill multiple destinations are implemented
        route.add(Route.DESTINATION);
        return route;
    }

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

    //TODO calculating a route with stops in between doesn't yet work.
//    public Route calculateNewAdvancedRoute() {
//        System.out.println("Calculating an advanced route");
//        System.out.println("Amount of destinations: " + this.destinationCoordsList.size());
//        addSingleRouteToTotal(calculateRouteBetweenTwoPoints(this.currentCoordX, this.currentCoordY,
//                getFirstDigitOfATwoDigitNumber(this.destinationCoordsList.get(0)),
//                getSeoncdDigitOfATwoDigitNumber(this.destinationCoordsList.get(0))));
//        for (int i = 0; i < this.destinationCoordsList.size() - 1; i++) {
//            System.out.println("Calculating the first destination");
//            addSingleRouteToTotal(calculateRouteBetweenTwoPoints(getFirstDigitOfATwoDigitNumber(this.destinationCoordsList.get(i)),
//                    getSeoncdDigitOfATwoDigitNumber(this.destinationCoordsList.get(i)),
//                    getFirstDigitOfATwoDigitNumber(this.destinationCoordsList.get(i + 1)),
//                    getSeoncdDigitOfATwoDigitNumber(this.destinationCoordsList.get(i + 1))));
//        }
//        System.out.println(this.routeToNavigate);
//        return new Route(this.routeToNavigate);
//    }

    public void printRoute(){
        System.out.print("RouteToNavigate: ");
        for (Integer i : this.routeToNavigate){
            System.out.print(i + ", ");
        }
        System.out.println("");
    }

    private int getFirstDigitOfATwoDigitNumber(int number) {
        if (number < 10) {
            return 0;
        }
        return number / 10;
    }

    private int getSeoncdDigitOfATwoDigitNumber(int number) {
        return number % 10;
    }
}