package Hardware;

import TI.BoeBot;
import TI.PinMode;
import TI.Timer;
import Utils.InfraredCallback;
import Utils.Updatable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for the hardware part Infrared-sensor. Contains methods to receive and interpret the signal
 * from the Infrared-sensor.
 * @see Utils.Updatable
 * @see Utils.InfraredCallback
 * @author Berend de Groot, Martijn de Kam, Meindert Kempe, Casper Lous
 */
public class InfraredReceiver implements Updatable {

    private int pinId;
    private InfraredCallback infraredCallback;
    private ArrayList<Integer> possibleButtons;
    private Timer infraredTimer = new Timer(20);

    // Binary representations of remote buttons.
    public static final int FORWARD  = 0b000010010000;
    public static final int BACKWARD = 0b000010010001;
    public static final int RIGHT    = 0b000010010010;
    public static final int LEFT     = 0b000010010011;
    public static final int ONE      = 0b000010000000;
    public static final int TWO      = 0b000010000001;
    public static final int THREE    = 0b000010000010;
    public static final int FOUR     = 0b000010000011;
    public static final int FIVE     = 0b000010000100;
    public static final int SIX      = 0b000010000101;
    public static final int SEVEN    = 0b000010000110;
    public static final int EIGHT    = 0b000010000111;
    public static final int NINE     = 0b000010001000;
    public static final int ZERO     = 0b000010001001;
    public static final int POWER    = 0b000010010101;
    public static final int TRIANGLE = 0b000111001000;
    public static final int TVVCR    = 0b000010100101;

    /**
     * Constructor for the infrared-sensor.
     * @param pinId            infraredsensor input signal pin id
     * @param infraredCallback infraredcallback object
     */
    public InfraredReceiver(int pinId, InfraredCallback infraredCallback) {
        this.pinId = pinId;
        BoeBot.setMode(this.pinId, PinMode.Input);
        this.infraredCallback = infraredCallback;
        this.possibleButtons = new ArrayList<>();
        Collections.addAll(this.possibleButtons, FORWARD, BACKWARD, RIGHT, LEFT, ONE, TWO, THREE, FOUR, FIVE, SIX,
                SEVEN, EIGHT, NINE, ZERO, POWER, TRIANGLE, TVVCR);
        this.infraredTimer.mark();
    }

    /**
     * Listens for the start-signal of an infrared remote.
     * @return true when a start-signal has been read.
     */
    private boolean listenForStartSignal() {
        int pulseLen = BoeBot.pulseIn(this.pinId, false, 6000);
        return pulseLen > 2000;
    }

    /**
     * Listens for an infrared bit-signal and puts the length of each low pulse in an array.
     * @return the length of 12 false infrared pulses received by the sensor.
     */
    private int[] listenForBitSignal() {
        int[] lengths = new int[12];
        for (int i = 0; i < 12; i++) {
            lengths[i] = BoeBot.pulseIn(this.pinId, false, 20000);
        }
        return lengths;
    }

    /**
     * Converts an array of infrared-bitsignal pulse lengths to a binary number.
     * @param rawBitSignal array of 12 pulse lengths.
     * @return binary number.
     */
    private int convertBitSignalToBinary(int[] rawBitSignal) {
        int bitSignalBinary = 0b0;
        for (int i = 11; i >= 0; i--) {
            if (rawBitSignal[i] > 900) {
                bitSignalBinary = bitSignalBinary << 1 | 1;
            } else {
                bitSignalBinary = bitSignalBinary << 1;
            }
        }
        System.out.println(Integer.toBinaryString(bitSignalBinary));
        return bitSignalBinary;
    }

    public ArrayList<Integer> getPossibleButtons() {
        return this.possibleButtons;
    }

    /**
     * If a start signal is received and then a bit signal is received, this signal is then converted to an integer
     * and passed to the callback. <p>
     * The <code>update()</code> method from <a href="{@docRoot}/Util/Updatable.html">Updatable</a>.
     * @see Updatable#update()
     */
    @Override
    public void update() {
        if (this.infraredTimer.timeout() && listenForStartSignal()) {
            this.infraredTimer.mark();
            this.infraredCallback.onInfraredButton(convertBitSignalToBinary(listenForBitSignal()));
        }
    }
}
