

import java.util.List;
import java.awt.*;
import java.util.Random;


/**
* 
* Models a vehicle and handles its state and behaviour during a simulation. 
* 
* @author MEZZO Inc.
* 
*/

public class Vehicle implements Runnable {
    private int currentX;
    private int currentY;
    public final int startX;
    public final int startY;
    public final int destX;
    public final int destY;
    public final int timePerSquare;
    private final Grid grid;
    private final Simulation sim;
    private final Logger logArea;
    private final Color color;
    private List<int[]> path;
    private int[] step;
    public long creationTime;
    public long startTime;
    public long endTime;

    public Vehicle(int startX, int startY, int destX, int destY, int timePerSquare, NorthFirst nf, Simulation sim) {
        this.startX = startX;
        this.startY = startY;
        this.currentX = startX;
        this.currentY = startY;
        this.destX = destX;
        this.destY = destY;
        this.timePerSquare = timePerSquare;
        this.sim = sim;
        this.grid = sim.getGrid();
        this.logArea = sim.getLogger();
        this.path = nf.findNorthFirstPath(grid, startX, startY, destX, destY);
        this.color = generateRandomColor();
    }

    public Vehicle(int startX, int startY, int destX, int destY, int timePerSquare, List<int[]> path, Simulation sim) {
        this.startX = startX;
        this.startY = startY;
        this.currentX = startX;
        this.currentY = startY;
        this.destX = destX;
        this.destY = destY;
        this.timePerSquare = timePerSquare;
        this.sim = sim;
        this.grid = sim.getGrid();
        this.logArea = sim.getLogger();
        this.path = path;
        this.color = generateRandomColor();
    }

    public List<int[]> getPath(){
        return path;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public Color getColor() {
        return color;
    }

    public int[] getStep(){
        return step;
    }

    @Override
    public void run() {
        try {
            creationTime = System.currentTimeMillis();
            occupy(new int[]{currentX, currentY});
            startTime = System.currentTimeMillis();
            move();
            endTime = System.currentTimeMillis();
            logArea.append("Vehicle " + this + " completed in " + (endTime - startTime) / 1000 + " seconds\n");
            sim.removeVehicle(this);
            grid.immediateRelease(currentX, currentY, this); // release the last grid square
            sim.refresh();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void occupy(int[] gridSquare) throws InterruptedException {
        logArea.append(this + " trying to occupy gridsquare " + gridSquare[0] + "," + gridSquare[1]);
        synchronized (grid) {

            while (grid.isOccupied(gridSquare[0], gridSquare[1])) {
                logArea.append(this + "waiting for (" + gridSquare[0] + "," + gridSquare[1] + ")");
                grid.wait();
            }
            logArea.append(gridSquare[0] + "," + gridSquare[1] + " gridsquare (" + gridSquare[0] + "," + gridSquare[1] + ")free");
            grid.occupy(gridSquare[0], gridSquare[1], this);
            sim.refresh();
        }

        logArea.append("gridsquare " + gridSquare + " occupied by " + this);

    }

    private void move() throws InterruptedException {
        for (int hop=1; hop<path.size();hop++) { // iterates over path from index 1 onwards

            step = path.get(hop);

            synchronized (sim) {
                while (sim.isPaused()) {
                    sim.wait();
                }
            }

            occupy(step); // wait until next grid square is free, then occupy it
            grid.release(currentX, currentY, timePerSquare * 1000); // schedule the release of current grid square

            currentX = step[0];
            currentY = step[1];
            sim.refresh(); // Update the grid panel
            Thread.sleep(timePerSquare * 1000);
        }
    }

    private Color generateRandomColor() {
        Random random = new Random();
        int red, green, blue;
        do {
            red = random.nextInt(256);
            green = random.nextInt(256);
            blue = random.nextInt(256);
        } while (red > 200 && green > 200 && blue > 200); // Avoid colors too close to white
        return new Color(red, green, blue);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "currentX=" + currentX +
                ", currentY=" + currentY +
                ", destX=" + destX +
                ", destY=" + destY +
                ", timePerSquare=" + timePerSquare +
                '}';
    }
}
