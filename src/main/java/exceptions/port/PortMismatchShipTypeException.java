package src.main.java.exceptions.port;

public class PortMismatchShipTypeException extends IllegalArgumentException {
    public PortMismatchShipTypeException() {
        super("The port does not provide operations for such ships.");
    }

}
