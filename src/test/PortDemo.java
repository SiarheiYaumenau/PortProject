package src.test;

import src.main.java.exceptions.port.PortMinMaxSizeException;
import src.main.java.exceptions.port.PortMismatchShipTypeException;
import src.main.java.exceptions.port.PortMismatchStatusException;
import src.main.java.exceptions.port.PortNotRegisteredShipException;
import src.main.java.port.TEUAndBulkerPort;
import src.main.java.ship.*;

import java.util.*;

public class PortDemo {
    public static void main(String[] args) {
        Ship blackCuttlefish = new ContainerShip("Black Cuttlefish", 2_000,
                "Porto", 400, ShipStatus.LOADING);
        Ship serenity = new ContainerShip("Serenity", 4_500,
                "Shanghai", 0, ShipStatus.READY_FOR_LOADING);
        Ship odyssey = new ContainerShip("Odyssey", 800,
                "Malaga", 800, ShipStatus.READY_FOR_UNLOADING);
        Ship pegasus = new GasCarrier("Pegasus", 80, "New York",
                0, ShipStatus.READY_FOR_LOADING);
        Ship voyager = new ContainerShip("Voyager", 2_508, "Malaga",
                1_361, ShipStatus.READY_FOR_UNLOADING);
        Ship ship6 = new Bulker("Phoenix", 150, "Cairo",
                34, ShipStatus.READY_FOR_LOADING);
        Ship calypso = new Bulker("Calypso", 120, "Malaga",
                98, ShipStatus.READY_FOR_UNLOADING);
        List<Ship> shipList = List.of(blackCuttlefish, serenity, odyssey, pegasus, voyager, ship6, calypso);
        TEUAndBulkerPort malaga = new TEUAndBulkerPort("Malaga" , 2_500, 861, 250, 31);
        for (Ship ship : shipList) {
            try {
                malaga.registerShip(ship);
            } catch (NullPointerException | PortMismatchShipTypeException | PortMismatchStatusException e) {
                System.out.println("Ship " + ship.getShipName() + " could not be registered. Reason: " + e.getMessage() + "\n");
            }
        }
        malaga.sortByDestinationPort();
        System.out.println("01." + malaga + "\n");
        System.out.println("02. Container ships are ready for unloading:\n" + malaga.getAllContainerShipsForUnloading() + "\n");
        System.out.println("TEU must be unloaded: " + malaga.calculateTEUWhichMustBeUnloaded() + "\n");

        //testing PortNotRegisteredShipException
        try {
            malaga.performLoadingOperationInPort(blackCuttlefish, 300);
        } catch (PortNotRegisteredShipException e) {
            System.out.println("testing PortNotRegisteredShipException: " + e.getMessage() + "\n");
        }

        voyager.setStatus(ShipStatus.UNLOADING);
        malaga.performLoadingOperationInPort(voyager, 1361);
        System.out.println("03." + malaga.toStringWithoutShips());
        System.out.println(malaga.getShips().stream().filter(ship -> ship.getShipName().equals("Voyager")).findFirst());
        System.out.println("TEU must be unloaded: " + malaga.calculateTEUWhichMustBeUnloaded() + "\n");

        //testing PortMinMaxSizeException
        odyssey.setStatus(ShipStatus.UNLOADING);
        try {
            malaga.performLoadingOperationInPort(odyssey, 279);
        } catch (PortMinMaxSizeException e) {
            System.out.println("testing PortMinMaxSizeException: " + e.getMessage() + "\n");
        }

        malaga.performLoadingOperationInPort(odyssey, 278);
        System.out.println("04." + malaga.toStringWithoutShips());
        System.out.println(malaga.getShips().stream().filter(ship -> ship.getShipName().equals("Odyssey")).findFirst() + "\n");

        serenity.setStatus(ShipStatus.LOADING);
        malaga.performLoadingOperationInPort(serenity, 1350);
        System.out.println("05." + malaga.toStringWithoutShips());
        System.out.println(malaga.getShips().stream().filter(ship -> ship.getShipName().equals("Serenity")).findFirst() + "\n");

        calypso.setStatus(ShipStatus.UNLOADING);
        malaga.performLoadingOperationInPort(calypso, 98);
        System.out.println("06." + malaga.toStringWithoutShips());
        System.out.println(malaga.getShips().stream().filter(ship -> ship.getShipName().equals("Calypso")).findFirst() + "\n");

        //testing PortMismatchStatusException
        try {
            malaga.performLoadingOperationInPort(voyager, 1);
        } catch (PortMismatchStatusException e) {
            System.out.println("testing PortMismatchStatusException: " + e.getMessage() + "\n");
        }

        System.out.println("Container ships sailed the port:\n" + malaga.removeShips(ship -> ship.getStatus() == ShipStatus.READY_TO_LEAVE_PORT) + "\n");
    }
}

