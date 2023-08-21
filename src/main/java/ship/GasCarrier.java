package src.main.java.ship;

public class GasCarrier extends Ship {

    public GasCarrier(String shipName, int loadCapacity, String destinationPort, int cargoesVolume,
                      ShipStatus status) {
        super(shipName, loadCapacity, destinationPort, cargoesVolume, status);
    }

    @Override
    public String toString() {
        return "Gas Carrier '" + shipName + '\'' +
                " (load capacity by megalitres: " + loadCapacity + "ml" +
                ", destination port: '" + destinationPort + '\'' +
                ", gas volume in the ship: " + cargoesVolume + "ml" +
                ", ship status is " + status +
                ", percentage of volume loading: " + loadingPercentage() + "%" +
                ')' + "\n";
    }
}
