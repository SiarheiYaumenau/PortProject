package src.test;

import src.main.java.exceptions.ship.ShipMaxSizeException;
import src.main.java.exceptions.ship.ShipMinSizeException;
import src.main.java.ship.*;

public class ShipDemo {
    public static void main(String[] args) {
        Ship blackCuttlefish = new ContainerShip("Black Cuttlefish", 2_000,
                "Porto", 400, ShipStatus.LOADING);
        Ship serenity = new ContainerShip("Serenity", 4_500,
                "Malaga", 0, ShipStatus.READY_FOR_LOADING);
        Ship odyssey = new ContainerShip("Odyssey", 800,
                "Shanghai", 800, ShipStatus.READY_FOR_UNLOADING);
        Ship pegasus = new GasCarrier("Pegasus", 80, "Oslo",
                0, ShipStatus.LOADING);
        Ship voyager = new GasCarrier("Voyager", 155, "Sidney",
                155, ShipStatus.UNLOADING);
        Ship phoenix = new Bulker("Phoenix", 150, "Cairo",
                34, ShipStatus.LOADING);
        Ship calypso = new Bulker("Calypso", 120, "New York",
                98, ShipStatus.UNLOADING);
        System.out.println(blackCuttlefish);
        System.out.println(blackCuttlefish.getFreeCapacity());
        blackCuttlefish.setCargoesVolume(2000);
        System.out.println(blackCuttlefish);
        System.out.println(serenity);
        serenity.setDestinationPort("Barcelona");
        serenity.setStatus(ShipStatus.LOADING);
        System.out.println(serenity);
        System.out.println(voyager);
        voyager.setCargoesVolume(0);
        voyager.setDestinationPort("Rotterdam");
        System.out.println(voyager);
        System.out.println(calypso);
        //testing ShipMaxSizeException
        try {
            phoenix.setCargoesVolume(151);
        } catch (ShipMaxSizeException e) {
            System.out.println(e.getMessage());
        }
        //testing ShipMinSizeException
        try {
            calypso.setCargoesVolume(-1);
        } catch (ShipMinSizeException e) {
            System.out.println(e.getMessage());
        }
    }
}

