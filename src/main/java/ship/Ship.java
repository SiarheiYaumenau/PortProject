package src.main.java.ship;

import src.main.java.exceptions.ship.ShipMaxSizeException;
import src.main.java.exceptions.ship.ShipMinSizeException;

import java.text.DecimalFormat;

public abstract class Ship {
    protected String shipName;
    protected int loadCapacity;
    protected String destinationPort;
    protected int cargoesVolume;
    protected int freeCapacity;
    protected ShipStatus status;

    public Ship(String shipName, int loadCapacity, String destinationPort, int cargoesVolume,
                ShipStatus status) throws ShipMaxSizeException, ShipMinSizeException {
        this.shipName = shipName;
        this.loadCapacity = loadCapacity;
        this.destinationPort = destinationPort;
        if (cargoesVolume > loadCapacity) {
            throw new ShipMaxSizeException(shipName);
        } else if (cargoesVolume < 0) {
            throw new ShipMinSizeException();
        } else {
            this.cargoesVolume = cargoesVolume;
        }
        this.freeCapacity = loadCapacity - cargoesVolume;
        this.status = status;
    }

    public String getShipName() {
        return shipName;
    }

    public int getLoadCapacity() {
        return loadCapacity;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort;
    }

    public int getCargoesVolume() {
        return cargoesVolume;
    }

    public void setCargoesVolume(int cargoesVolume) throws ShipMaxSizeException {
        if (cargoesVolume > loadCapacity) {
            throw new ShipMaxSizeException(shipName);
        } else if (cargoesVolume < 0) {
            throw new ShipMinSizeException();
        } else {
            this.cargoesVolume = cargoesVolume;
            this.freeCapacity = loadCapacity - cargoesVolume;
            if (cargoesVolume == loadCapacity) {
                this.status = ShipStatus.READY_TO_LEAVE_PORT;
            }
            if (cargoesVolume == 0) {
                this.status = ShipStatus.READY_TO_LEAVE_PORT;
            }
        }
    }

    public int getFreeCapacity() {
        return freeCapacity;
    }

    public ShipStatus getStatus() {
        return status;
    }

    public void setStatus(ShipStatus status) {
        this.status = status;
    }

    public double loadingPercentage() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        double percentage = ((double) cargoesVolume / loadCapacity) * 100;
        String formattedValue = decimalFormat.format(percentage);
        return Double.parseDouble(formattedValue);
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
