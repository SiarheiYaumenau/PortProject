package src.main.java.ship;

public class ContainerShip extends Ship {


    public ContainerShip(String shipName, int loadCapacity, String destinationPort, int cargoesVolume,
                         ShipStatus status) {
        super(shipName, loadCapacity, destinationPort, cargoesVolume, status);
    }

    @Override
    public String toString() {
        return "Container Ship '" + shipName + '\'' +
                " (load capacity by containers: " + loadCapacity + "TEU" +
                ", destination port: '" + destinationPort + '\'' +
                ", containers in the ship: " + cargoesVolume + "TEU" +
                ", ship status is " + status +
                ", percentage of containers loading: " + loadingPercentage() + "%" +
                ')' + "\n";
    }
}
