package Hardware;

import TI.BoeBot;
import TI.PinMode;
import Utils.InfraredCallback;
import Utils.Updatable;

import java.util.HashMap;

/**
 * Class for the hardware part infraredsensor, contains methods to receive and interprit the signal from the infraredsensor.
 */
public class InfraredReceiver implements Updatable {

    private int pinId;
    private InfraredCallback infraredCallback;

    private HashMap<Integer, String> remoteButtons;


    /**
     * Constructor for the infraredsensor
     * @param pinId infraredsensor input signal pin id
     * @param infraredCallback infraredcallback object
     */
    public InfraredReceiver(int pinId, InfraredCallback infraredCallback){
        this.pinId = pinId;
        BoeBot.setMode(this.pinId, PinMode.Input);
        this.infraredCallback = infraredCallback;
        this.remoteButtons = new HashMap<>();

        // Hashmap van de verschillende knoppen voor de infrarood afstandsbediening.
        // De key is het bit signaal dat wordt gestuurd, de value is de bijbehorende knop in een String.
        this.remoteButtons.put(0b000010010000, "ch+");
        this.remoteButtons.put(0b000010010001, "ch-");
        this.remoteButtons.put(0b000010010010, "vol+");
        this.remoteButtons.put(0b000010010011, "vol-");
        this.remoteButtons.put(0b000010000000, "1");
        this.remoteButtons.put(0b000010000001, "2");
        this.remoteButtons.put(0b000010000010, "3");
        this.remoteButtons.put(0b000010000011, "4");
        this.remoteButtons.put(0b000010000100, "5");
        this.remoteButtons.put(0b000010000101, "6");
        this.remoteButtons.put(0b000010000110, "7");
        this.remoteButtons.put(0b000010000111, "8");
        this.remoteButtons.put(0b000010001000, "9");
        this.remoteButtons.put(0b000010001001, "0");
        this.remoteButtons.put(0b000010010101, "power");
    }

    /**
     * Listens for the startsignal of an infrared remote
     * @return true when a startsignal has been read.
     */
    private boolean listenForStartSignal(){
        // TODO, kijken of de wachttijd van de pulsein te lang is of niet, kan misschien efficienter.
        int pulseLen = BoeBot.pulseIn(this.pinId, false, 6000);
        return pulseLen > 2000;
    }

    /**
     * Listens for an infrared bit-signal and puts the length of each low pulse in an array.
     * @return the length of 12 false infrared pulses received by the sensor.
     */
    private int[] listenForBitSignal(){
        int[] lengths = new int[12];
        for (int i = 0; i < 12; i++) {
            // TODO, kijken of de wachttijd van de pulsein te lang is of niet, kan misschien efficienter.
            lengths[i] = BoeBot.pulseIn(this.pinId, false, 20000);
        }
        return lengths;
    }

    /**
     * Converts an array of infrared-bitsignal pulse lengths to a binary number.
     * @param rawBitSignal array of 12 pulse lengths
     * @return binary number
     */
    private int convertBitSignalToBinary(int[] rawBitSignal){
        int bitSignalBinary = 0b0;
        for (int i = 11; i >= 0; i--){
            if (rawBitSignal[i] > 900){
                bitSignalBinary = bitSignalBinary << 1 | 1;
            } else {
                bitSignalBinary = bitSignalBinary << 1;
            }
        }
        return bitSignalBinary;
    }

    /**
     * Interprits an infrareed signal in the form of a binary number and translates it to the String of the attached button on the infrared remote.
     * @return String of infrared remote button corresponding to the found infrared signal, if no corresponding button exists then null is returned.
     */
    private String getPressedremoteButtons(){
        int bitSignalBinary = convertBitSignalToBinary(listenForBitSignal());
        return this.remoteButtons.getOrDefault(bitSignalBinary, null);
    }

    /**
     * If a startsignal is received, then a bit-signal is received and the corresponding infrared remote button is given to the callback object.
     */
    @Override
    public void update() {
        if (listenForStartSignal()){
            this.infraredCallback.OnInfraredButton(getPressedremoteButtons());
        }
    }
}
