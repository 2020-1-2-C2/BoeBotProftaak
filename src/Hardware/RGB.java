package Hardware;

import TI.BoeBot;

public class RGB extends Led{
    private int red;
    private int green;
    private int blue;
    private int pinRed;
    private int pinGreen;
    private int pinBlue;

    public RGB(int pinId, int red, int green, int blue, int pinRed, int pinGreen, int pinBlue) {
        super(pinId);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.pinRed = pinRed;
        this.pinGreen = pinGreen;
        this.pinBlue = pinBlue;
    }

    public RGB(int pinId, int red, int green, int blue) {
        super(pinId);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Mixes the colors
     * @param red boolean to turn the color on or off.
     * @param green boolean to turn the color on or off.
     * @param blue boolean to turn the color on or off.
     */
    public void setColor(boolean red, boolean green, boolean blue) {
        BoeBot.digitalWrite(this.pinRed, red);
        BoeBot.digitalWrite(this.pinGreen, green);
        BoeBot.digitalWrite(this.pinBlue, blue);
    }

    @Override
    public void on() {
        super.on();
    }

    @Override
    public void off() {
        super.off();
    }

    @Override
    public boolean getIsOn() {
        return super.getIsOn();
    }

    public int getBlue() {
        return this.blue;
    }

    public int getGreen() {
        return this.green;
    }

    public int getRed() {
        return this.red;
    }
}
