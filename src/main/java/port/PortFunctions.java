package src.main.java.port;

import src.main.java.exceptions.port.PortMinMaxSizeException;
import src.main.java.exceptions.port.PortMismatchShipTypeException;
import src.main.java.exceptions.port.PortMismatchStatusException;
import src.main.java.exceptions.port.PortNotRegisteredShipException;
import src.main.java.ship.Ship;

import java.util.List;
import java.util.function.Predicate;

/**
 * This interface describes the functions of the Port.
 */
public interface PortFunctions {

    /**
     * Registers a ship in the port for loading or unloading operations.
     *
     * @param ship The ship to be registered in the port.
     * @throws NullPointerException        If the provided ship is null.
     * @throws PortMismatchShipTypeException If the ship's type is not compatible with the port's supported ship types.
     * @throws PortMismatchStatusException   If the ship's status is not suitable for registration in the port.
     */
    void registerShip(Ship ship);

    /**
     * Searches for ships in the port that match the given predicate.
     *
     * @param predicate The predicate used to filter ships.
     * @return A list of ships that satisfy the predicate's condition.
     * @apiNote This method searches for ships in the port that match the condition specified by the provided predicate.
     * It iterates through the list of registered ships in the port and applies the predicate's test method to each ship.
     * If the predicate evaluates to true for a ship, it is included in the resulting list of selected ships.
     * The selected ships are returned in a new list.
     */
    List<Ship> searchShips(Predicate<Ship> predicate);

    /**
     * Retrieves a list of ContainerShips ready for unloading in the port.
     *
     * @return A list of ContainerShips that are ready for unloading.
     * @apiNote This method searches for ContainerShips in the port that have a status of READY_FOR_UNLOADING and have
     * sufficient cargo space available for unloading. It filters the list of registered ships by using the provided
     * predicate to match ContainerShips with the specified conditions. The resulting list contains ContainerShips
     * that are ready for unloading and have enough space in their cargo holds to accommodate additional cargo.
     */
    List<Ship> getAllContainerShipsForUnloading ();

    /**
     * Calculates the total TEU (Twenty-foot Equivalent Unit) to be unloaded in the port.
     *
     * @return The total TEU to be unloaded in the port across all ContainerShips in the ready or unloading status.
     * @apiNote This method calculates the total TEU to be unloaded in the port by summing up the cargo volume of all
     * ContainerShips that are in the READY_FOR_UNLOADING or UNLOADING status. It filters the list of registered ships
     * using a predicate to select ContainerShips with the specified statuses. The method then iterates through the list
     * of selected ships and accumulates their cargo volumes to compute the total TEU to be unloaded.
     * The calculated TEU value is returned as the result.
     */
    int calculateTEUWhichMustBeUnloaded();

    /**
     * Sorts the list of ships in the port based on their destination ports.
     */
    void sortByDestinationPort();

    /**
     * Performs a loading or unloading operation for a ship in the port.
     *
     * @param ship  The ship to be loaded or unloaded.
     * @param cargo The amount of cargo to be loaded or unloaded.
     * @throws PortNotRegisteredShipException If the ship is not registered in the port.
     * @throws PortMismatchStatusException    If the ship's status is not suitable for loading or unloading.
     * @throws PortMinMaxSizeException        If the loading or unloading operation exceeds the capacity limits of the port.
     * @apiNote This method allows loading or unloading of cargoes for the specified ship within the port. The method
     * verifies whether the ship is registered in the port and its current status allows the loading or unloading operation.
     * Additionally, it checks if the loading or unloading would exceed the capacity limits of the port.
     * If any of these conditions are not met, the corresponding exceptions are thrown. The cargoes are loaded or
     * unloaded based on the ship's type, and the respective port cargo counters are updated.
     * Note that any ShipMaxSizeException or ShipMinSizeException caught during the operation will be printed to the
     * console, but they will not be propagated as exceptions.
     */
    void performLoadingOperationInPort(Ship ship, int cargo);

    /**
     * Removes ships from the port that match the specified predicate and sets their status to SAILED.
     *
     * @param predicate The predicate used to select ships for removal.
     * @return A list of ships that were removed from the port and had their status set to SAILED.
     * @apiNote This method removes ships from the port that match the specified condition defined by the provided predicate.
     * The method first selects the ships that satisfy the predicate's condition using the internal method searchShips().
     * It then iterates through the list of selected ships and sets their status to SAILED, indicating that they have
     * departed from the port. Finally, the method removes the selected ships from the list of registered ships in the port.
     * The ships that were removed and had their status set to SAILED are returned in a new list.
     */
    List<Ship> removeShips(Predicate<Ship> predicate);

}
