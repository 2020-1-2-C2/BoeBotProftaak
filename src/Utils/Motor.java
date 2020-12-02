package Utils;

public interface Motor extends Updatable{
    void goToSpeed(int speed, int time);
    void goToSpeedLeft(int speed, int time);
    void goToSpeedRight(int speed, int time);
    void emergencyStop();
    void getSpeedLeft();
    void getSpeedRight();
}
