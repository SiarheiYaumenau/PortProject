package src.main.java.exceptions.ship;

public class ShipMaxSizeException extends IndexOutOfBoundsException {
    public ShipMaxSizeException(String shipName) {
        super("Ship " + shipName + " will be overloaded and the cargo could not be added.");
    }
}
