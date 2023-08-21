package src.main.java.exceptions.port;

public class PortMismatchStatusException extends IllegalArgumentException {
    public PortMismatchStatusException() {
        super("Mismatch between the ship status and condition of ready for operations.");
    }

    public PortMismatchStatusException(String s) {
        super(s);
    }
}
