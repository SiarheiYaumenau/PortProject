package src.main.java.ship;

public class Bulker extends Ship {


    public Bulker(String shipName, int loadCapacity, String destinationPort, int cargoesVolume,
                  ShipStatus status) {
        super(shipName, loadCapacity, destinationPort, cargoesVolume, status);
    }

    @Override
    public String toString() {
        return "Bulker '" + shipName + '\'' +
                " (load capacity by kilo tonnes: " + loadCapacity + "kt" +
                ", destination port: '" + destinationPort + '\'' +
                ", cargo weight in the ship: " + cargoesVolume + "kt" +
                ", ship status is " + status +
                ", percentage of weight loading: " + loadingPercentage() + "%" +
                ')' + "\n";
    }
}
