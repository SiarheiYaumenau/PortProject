package src.main.java.port;

import src.main.java.exceptions.port.PortMinMaxSizeException;
import src.main.java.exceptions.port.PortMismatchShipTypeException;
import src.main.java.exceptions.port.PortMismatchStatusException;
import src.main.java.exceptions.port.PortNotRegisteredShipException;
import src.main.java.exceptions.ship.ShipMaxSizeException;
import src.main.java.exceptions.ship.ShipMinSizeException;
import src.main.java.ship.Bulker;
import src.main.java.ship.ContainerShip;
import src.main.java.ship.Ship;
import src.main.java.ship.ShipStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class TEUAndBulkerPort implements PortFunctions {
    private final String name;
    private final int TEUCapacity;
    private int TEUInPort;
    private final int bulkerCapacity;
    private int bulkerCargoesInPort;
    private final List<Ship> ships;

    /**
     * Creates a port with different type of berths with its
     * cargo capacity of the port and an empty set of ships.
     *
     * @param name                The name of a port.
     * @param TEUCapacity         The capacity of TEU (Twenty-foot Equivalent Unit) for the port.
     * @param TEUInPort           The current TEU (Twenty-foot Equivalent Unit) in the port.
     * @param bulkerCapacity      The capacity of bulk cargo for the port.
     * @param bulkerCargoesInPort The current amount of bulk cargo in the port.
     */
    public TEUAndBulkerPort(String name, int TEUCapacity, int TEUInPort, int bulkerCapacity, int bulkerCargoesInPort) {
        this.name = name;
        this.TEUCapacity = TEUCapacity;
        this.TEUInPort = TEUInPort;
        this.bulkerCapacity = bulkerCapacity;
        this.bulkerCargoesInPort = bulkerCargoesInPort;
        this.ships = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getTEUCapacity() {
        return TEUCapacity;
    }

    public int getTEUInPort() {
        return TEUInPort;
    }

    public void setTEUInPort(int TEUInPort) {
        this.TEUInPort = TEUInPort;
    }

    public int getBulkerCapacity() {
        return bulkerCapacity;
    }

    public int getBulkerCargoesInPort() {
        return bulkerCargoesInPort;
    }

    public void setBulkerCargoesInPort(int bulkerCargoesInPort) {
        this.bulkerCargoesInPort = bulkerCargoesInPort;
    }

    public List<Ship> getShips() {
        return ships;
    }

    @Override
    public void registerShip(Ship ship) {
        if (ship == null) {
            throw new NullPointerException("Ship can not be null");
        }
        if (!(ship instanceof ContainerShip || ship instanceof Bulker)) {
            throw new PortMismatchShipTypeException();
        }
        if (ship.getStatus() == ShipStatus.READY_FOR_LOADING || ship.getStatus() == ShipStatus.READY_FOR_UNLOADING) {
            ships.add(ship);
        } else {
            throw new PortMismatchStatusException("Ship status must be READY_FOR_LOADING or READY_FOR_UNLOADING.");
        }
    }

    @Override
    public List<Ship> searchShips(Predicate<Ship> predicate) {
        List<Ship> selectedShips = new ArrayList<>();
        for (Ship ship : ships) {
            if (predicate.test(ship)) {
                selectedShips.add(ship);
            }
        }
        return selectedShips;
    }

    @Override
    public List<Ship> getAllContainerShipsForUnloading() {
        return searchShips(ship -> (ship instanceof ContainerShip && ship.getStatus() == ShipStatus.READY_FOR_UNLOADING &&
                ship.getCargoesVolume() <= (TEUCapacity - TEUInPort)));
    }

    @Override
    public int calculateTEUWhichMustBeUnloaded() {
        List<Ship> shipsForUnloading = searchShips(ship -> (ship instanceof ContainerShip &&
                (ship.getStatus() == ShipStatus.READY_FOR_UNLOADING || ship.getStatus() == ShipStatus.UNLOADING)));
        int TEU = 0;
        for (Ship ship : shipsForUnloading) {
            TEU += ship.getCargoesVolume();
        }
        return TEU;
    }

    @Override
    public void sortByDestinationPort() {
        ships.sort(Comparator.comparing(Ship::getDestinationPort));
    }

    @Override
    public void performLoadingOperationInPort(Ship ship, int cargo) throws PortNotRegisteredShipException,
            PortMismatchStatusException, PortMinMaxSizeException {
        if (!ships.contains(ship)) {
            throw new PortNotRegisteredShipException();
        }
        ShipStatus currentStatus = ship.getStatus();
        boolean isContainerShip = ship instanceof ContainerShip;
        if (currentStatus == ShipStatus.READY_TO_LEAVE_PORT || currentStatus == ShipStatus.READY_FOR_LOADING ||
                currentStatus == ShipStatus.READY_FOR_UNLOADING || currentStatus == ShipStatus.SAILED) {
            throw new PortMismatchStatusException("Ship status must be LOADING or UNLOADING.");
        }
        if ((currentStatus == ShipStatus.LOADING && isContainerShip && TEUInPort - cargo < 0) ||
                (currentStatus == ShipStatus.LOADING && !isContainerShip && bulkerCargoesInPort - cargo < 0) ||
                (currentStatus == ShipStatus.UNLOADING && isContainerShip && TEUInPort + cargo > TEUCapacity) ||
                (currentStatus == ShipStatus.UNLOADING && !isContainerShip && bulkerCargoesInPort + cargo > bulkerCapacity)) {
            throw new PortMinMaxSizeException();
        }
        try {
            if (ship.getStatus() == ShipStatus.LOADING) {
                ship.setCargoesVolume(ship.getCargoesVolume() + cargo);
                if (ship instanceof ContainerShip) {
                    TEUInPort -= cargo;
                } else {
                    bulkerCargoesInPort -= cargo;
                }
            } else {
                ship.setCargoesVolume(ship.getCargoesVolume() - cargo);
                if (ship instanceof ContainerShip) {
                    TEUInPort += cargo;
                } else {
                    bulkerCargoesInPort += cargo;
                }
            }
        } catch (ShipMaxSizeException | ShipMinSizeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Ship> removeShips(Predicate<Ship> predicate) {
        List<Ship> selectedShips = searchShips(predicate);
        for (Ship ship : selectedShips) {
            if (predicate.test(ship)) {
                ship.setStatus(ShipStatus.SAILED);
                ships.remove(ship);
            }
        }
        return selectedShips;
    }

    @Override
    public String toString() {
        return name + " is TEU and bulker port with the next characteristics: " +
                "TEU capacity = " + TEUCapacity +
                ", TEU in port = " + TEUInPort +
                ", bulker capacity = " + bulkerCapacity +
                ", bulker cargoes in port = " + bulkerCargoesInPort +
                ", registered ships: " + ships +
                ';';
    }

    public String toStringWithoutShips() {
        return name + " is TEU and bulker port with the next characteristics: " +
                "TEU capacity = " + TEUCapacity +
                ", TEU in port = " + TEUInPort +
                ", bulker capacity = " + bulkerCapacity +
                ", bulker cargoes in port = " + bulkerCargoesInPort +
                ';';
    }
}
