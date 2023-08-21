package src.main.java.exceptions.ship;

public class ShipMinSizeException extends IndexOutOfBoundsException {
    public ShipMinSizeException() {
        super("Cargoes in the ship should be more than 0. Please check a cargo value in the ship.");
    }
}
