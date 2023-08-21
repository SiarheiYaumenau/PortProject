package src.main.java.exceptions.port;

public class PortNotRegisteredShipException extends IllegalArgumentException {
    public PortNotRegisteredShipException() {
        super("The ship is not registered in the port.");
    }

}
