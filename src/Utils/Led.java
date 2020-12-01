package Utils;

public interface Led extends Updatable {
    void on();
    void off();
    void fade(int fade);
    void blink(int interval);
    boolean getIsOn();
}
