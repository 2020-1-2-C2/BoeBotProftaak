package Hardware;

import TI.BoeBot;
import Utils.InfraredCallback;
import Utils.Updatable;

import java.util.HashMap;

public class InfraredReceiver implements Updatable {

    private int pinId;
    private boolean isDetected;
    private InfraredCallback infraredCallback;

    private HashMap<Integer, String> remoteButtons;


    /**
     * Constructor voor Hardware.InfraredReceiver
     * @param pinId
     * @param infraredCallback
     */
    public InfraredReceiver(int pinId, InfraredCallback infraredCallback){
        this.pinId = pinId;
        this.infraredCallback = infraredCallback;
        this.remoteButtons = new HashMap<>();

        this.remoteButtons.put(0b000010010000, "ch+");
        this.remoteButtons.put(0b000010010001, "ch-");
        this.remoteButtons.put(0b000010010010, "vol+");
        this.remoteButtons.put(0b000010010011, "vol-");
        this.remoteButtons.put(0b000010000000, "1");
        this.remoteButtons.put(0b000010000001, "2");
        this.remoteButtons.put(0b000010000010, "3");
        this.remoteButtons.put(0b000010010101, "power");

    }

    /**
     * Kijkt of er een startsignaal van een infrarood-afstandsbediening wordt ontvangen.
     * @return true wanneer er een startsignaal binnenkomt.
     */
    public boolean listenForStartSignal(){
        int pulseLen = BoeBot.pulseIn(this.pinId, false, 6000);
        return pulseLen > 2000;
    }

    /**
     * Ontvangt een infrarood-bitsignaal, en zet dit om naar een Array van de lengtes van de signalen.
     * @return
     */
    public int[] listenForBitSignal(){
        int lengths[] = new int[12];
        for (int i = 0; i < 12; i++) {
            lengths[i] = BoeBot.pulseIn(0, false, 20000);
        }
        return lengths;
    }

    /**
     * Zet een infrarood-bitsignaal om naar een binair getal.
     * @param rawBitSignal
     * @return
     */
    public int convertBitSignalToBinary(int[] rawBitSignal){
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
     * Zet een ontvangen infrarood-signaal in de vorm van een binair getal om in een string van de desbetreffende knop.
     * @return
     */
    public String getPressedremoteButtons(){
        int bitSignalBinary = convertBitSignalToBinary(listenForBitSignal());
        if (this.remoteButtons.containsKey(bitSignalBinary)){
            return this.remoteButtons.get(bitSignalBinary);
        } else {
            return null;
        }
    }

    /**
     * Als er een startsignaal is ontvangen, wordt er een bitsignaal ontvangen en wordt de desbetreffende knop mee gegeven aan de Utils.InfraredCallback.
     */
    @Override
    public void update() {
        if (listenForStartSignal()){
            this.infraredCallback.OnInfraredCode(getPressedremoteButtons());
        }
    }
}
