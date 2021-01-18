package utils;

/**
 * Interface for the InfraredCallback interface which acts as a pass through.
 * @author Martijn de Kam
 */
public interface InfraredControllerCallback {

    void onInfraredControllerCommand(int button);
}
