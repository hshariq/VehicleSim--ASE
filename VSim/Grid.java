
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Manages a rectangular grid, and the occupation of each grid square with
 * {@link Vehicle} instances.
 * 
 * @author MEZZO Inc.
 * 
 */

public class Grid {
    private final int rows;
    private final int cols;
    private final Map<String, Vehicle> occupiedSquares;

    // Constructor
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        // initialize the map to keep track of occupied squares
        this.occupiedSquares = new HashMap<>();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public synchronized boolean isOccupied(int x, int y) {
        return occupiedSquares.containsKey(x + "," + y);
    }

    public synchronized void occupy(int x, int y, Vehicle vehicle) {
        occupiedSquares.put(x + "," + y, vehicle);
    }

    public synchronized void immediateRelease(int x, int y, Vehicle vehicle) {
        if (occupiedSquares.get(x + "," + y) == vehicle) {
            occupiedSquares.remove(x + "," + y);
            notifyAll();
        } else {

            System.out.println("FATAL ERROR");
        }

    }

    public synchronized void release(int x, int y, int delay) {
        Vehicle vehicle = occupiedSquares.get(x + "," + y);
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                immediateRelease(x, y, vehicle);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public synchronized Vehicle getOccupyingVehicle(int x, int y) {
        return occupiedSquares.get(x + "," + y);
    }
}
