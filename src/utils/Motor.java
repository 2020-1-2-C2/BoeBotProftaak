package utils;

/**
 * General motor interface so the specific motor on the bot can implement the interface and the bots logic can still call these methods.
 * @see utils.Updatable
 * @author Meindert Kempe, Tom Martens, Martijn de Kam, Berend de Groot
 */
public interface Motor extends Updatable {

    /**
     * Got to a certain speed over a certain time
     *
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     */
    void goToSpeed(int speed);

    /**
     * Got to a certain speed over a certain time
     *
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     */
    void goToSpeedLeft(int speed);

    /**
     * Got to a certain speed over a certain time.
     *
     * @param speed Percentage of the maximum speed (100 max forwards, -100 max backwards).
     */
    void goToSpeedRight(int speed);

    /**
     * Stop as soon as possible.
     */
    void emergencyStop();

    /**
     * Stop immediatly without it being an emergency
     */
    void immediateStop();

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
