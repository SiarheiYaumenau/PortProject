package src.main.java.exceptions.port;

public class PortMinMaxSizeException extends IndexOutOfBoundsException {
    public PortMinMaxSizeException() {
        super("The operation leads to going beyond the limits of the port infrastructure.");
    }
}
