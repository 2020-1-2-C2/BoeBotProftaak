package Utils;

/**
 * General motor interface so the specific motor on the bot can implement the interface and the bots logic can still call these methods.
 * @see Utils.Updatable
 * @author Meindert Kempe, Tom Martens, Martijn de Kam, Berend de Groot
 */
public interface Motor extends Updatable {

    /**
     * Got to a certain speed over a certain time
     *
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     * @param time  Time in milliseconds over which to accelerate.
     */
    void goToSpeed(int speed, int time);

    /**
     * Got to a certain speed over a certain time
     *
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     * @param time  Time in milliseconds over which to accelerate.
     */
    void goToSpeedLeft(int speed, int time);

    /**
     * Got to a certain speed over a certain time.
     *
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     * @param time  Time in milliseconds over which to accelerate.
     */
    void goToSpeedRight(int speed, int time);

    /**
     * Stop as soon as possible.
     */
    void emergencyStop();

    /**
     * Get the current speed in a percentage of the maximum speed.
     *
     * @return Percentage of the maximum speed (100 max forwards, -100 max backwards).
     */
    int getSpeedLeft();

    /**
     * Get the current speed in a percentage of the maximum speed.
     *
     * @return percentage of the maximum speed (100 max forwards, -100 max backwards).
     */
    int getSpeedRight();
}
