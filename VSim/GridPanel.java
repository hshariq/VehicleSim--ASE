
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
* 
* Graphical user interface representation of a grid square. Responsible for drawing its occupying {@link Vehicle} and the segment of the Vehicle's path that intersects itself. 
* 
* @author MEZZO Inc.
* 
*/


public class GridPanel extends JPanel {
    private final int cellSize;

    private final JTextArea logArea;
    public Grid grid;


    public GridPanel(int cellSize, JTextArea logArea, Grid grid) {
        this.cellSize = cellSize;
        this.logArea = logArea;
        this.grid = grid;
        setPreferredSize(new Dimension(grid.getCols() * cellSize, grid.getRows() * cellSize));
    }

    public void log(String s){

        logArea.append(s);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawVehicles(g);
        drawVehiclePaths(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= grid.getRows(); i++) {
            g.drawLine(0, i * cellSize, grid.getCols() * cellSize, i * cellSize);
        }
        for (int i = 0; i <= grid.getCols(); i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, grid.getRows() * cellSize);
        }
    }

    private void drawVehicles(Graphics g) {
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                Vehicle occupyingVehicle = grid.getOccupyingVehicle(col, row);
                if (occupyingVehicle != null) {
                    g.setColor(occupyingVehicle.getColor());
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                }
            }
        }
    }


    private void drawVehiclePaths(Graphics g) {
        ArrayList<Vehicle> vehList = getActiveVehicles();
        for (Vehicle veh : vehList)  {
            g.setColor(veh.getColor());

            List<int[]> vehPath = veh.getPath();
            for(int i=0; i<vehPath.size()-1;i++){

                int[] src = vehPath.get(i);
                int[] dst = vehPath.get(i+1);

                g.drawLine((src[0]* cellSize)+cellSize/2, (src[1]* cellSize)+cellSize/2, (dst[0]* cellSize)+cellSize/2, (dst[1]* cellSize)+cellSize/2);


            }

        }
    }


    private ArrayList<Vehicle> getActiveVehicles(){
        HashSet<Vehicle> vehList = new HashSet<Vehicle>();
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                Vehicle occupyingVehicle = grid.getOccupyingVehicle(col, row);
                if (occupyingVehicle != null) {

                    vehList.add(occupyingVehicle);
                }
            }
        }

        return new ArrayList<Vehicle>(vehList);

    }



}
