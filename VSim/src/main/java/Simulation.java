
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * Manages the simulation. Responsible for adding and removing vehicles, pausing
 * and resuming simulation, assigning a log manager and incorporating a
 * graphical user interface.
 * 
 * @author MEZZO Inc.
 * 
 */

public class Simulation {

    private final List<Vehicle> vehicles;
    GridPanel panel;
    Grid grid;
    private boolean hasGUI = false;
    private boolean paused = false;
    private Logger logger;

    public Simulation(Grid grid) {
        // gives access to a 20*20 data structures
        this.grid = grid;
        // vehicles list to keep track of vehicles in simulation
        // copyonwritearraylist is thread safe- multiple vehicles will be accessing it
        this.vehicles = new CopyOnWriteArrayList<>();

    }

    public Simulation(GridPanel panel) {

        this.panel = panel;
        this.grid = panel.grid;
        // indicates that this simulation has a gui
        this.hasGUI = true;
        this.vehicles = new CopyOnWriteArrayList<>();

    }

    public Grid getGrid() {

        return grid;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        if (hasGUI)
            panel.log(" Vehicle added: " + vehicle + "\n");
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
        if (hasGUI)
            panel.log("Vehicle removed: " + vehicle + "\n");
    }

    public void setLogger(Logger logger) {

        this.logger = logger;

    }

    public Logger getLogger() {

        return logger;

    }

    public synchronized void setPaused(boolean paused) {
        this.paused = paused;
        notifyAll();
    }

    public synchronized boolean isPaused() {
        return paused;
    }

    public synchronized void refresh() {
        if (hasGUI)
            panel.repaint();
        notifyAll();
    }

}
