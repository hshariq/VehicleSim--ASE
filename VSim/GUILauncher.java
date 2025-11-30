
import javax.swing.*;
import java.awt.*;

/**
 * 
 * Launches the simulator graphical user interface.
 * 
 * @author MEZZO Inc.
 * 
 */

public class GUILauncher {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(GUILauncher::createAndShowGUI);

    }

    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Vehicle Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        int rows = 20;
        int cols = 20;
        int cellSize = 30;
        // defines th rows and columns of the grid- Class Grid.java
        Grid grid = new Grid(rows, cols);

        // Text area for logging- part of Java Swing library
        JTextArea logArea = new JTextArea(10, 40);
        // cant be edited only readable
        logArea.setEditable(false);

        // create a grid panel to show the simlation
        GridPanel gridPanel = new GridPanel(cellSize, logArea, grid);
        // we have basically defined each cell, the grid that it has to be, and log area
        frame.add(gridPanel, BorderLayout.CENTER);

        // now the gui is made, we will create the simulation object
        // links the simulation object to the grid panel
        Simulation sim = new Simulation(gridPanel);
        // assign a logger - so now we have gui fully linked to simulation
        // now changes in simulation will be reflected in gui
        sim.setLogger(new Logger(logArea));

        // Control panel for adding vehicles- all gui stuff
        JPanel controlPanel = new JPanel();
        JTextField startXField = new JTextField(3);
        JTextField startYField = new JTextField(3);
        JTextField destXField = new JTextField(3);
        JTextField destYField = new JTextField(3);
        JTextField timePerSquareField = new JTextField(3);

        // when press button add vehicle with given parameters
        JButton addButton = new JButton("Add Vehicle");
        addButton.addActionListener(e -> {
            int startX = Integer.parseInt(startXField.getText());
            int startY = Integer.parseInt(startYField.getText());
            int destX = Integer.parseInt(destXField.getText());
            int destY = Integer.parseInt(destYField.getText());
            int timePerSquare = Integer.parseInt(timePerSquareField.getText());

            // vehicl object created with northfirst strategy by default- vehicle.java
            Vehicle vehicle = new Vehicle(startX, startY, destX, destY, timePerSquare, new NorthFirst(), sim);
            // vehicle is added to simulation
            sim.addVehicle(vehicle);
            // vehicle thread is started
            new Thread(vehicle).start();
        });

        JButton startPauseButton = new JButton("Start/Pause");
        startPauseButton.addActionListener(e -> {
            // in simulation.java
            if (sim.isPaused()) {
                sim.setPaused(false);
                startPauseButton.setText("Pause");
            } else {
                sim.setPaused(true);
                startPauseButton.setText("Start");
            }
        });

        controlPanel.add(new JLabel("Start X:"));
        controlPanel.add(startXField);
        controlPanel.add(new JLabel("Start Y:"));
        controlPanel.add(startYField);
        controlPanel.add(new JLabel("Destination X:"));
        controlPanel.add(destXField);
        controlPanel.add(new JLabel("Destination Y:"));
        controlPanel.add(destYField);
        controlPanel.add(new JLabel("Time per Square (s):"));
        controlPanel.add(timePerSquareField);
        controlPanel.add(addButton);
        controlPanel.add(startPauseButton);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(logArea), BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        // in gridscenario generator we have made a scenario of 25 vehicles
        // in gridscenario.java
        // the seed is given so that everytime same scenario is generated
        GridScenarioGenerator scengen = new GridScenarioGenerator(42, sim);

        Vehicle[] scenario = scengen.generateScenario(25, 1);

        for (int v = 0; v < scenario.length; v++) {

            sim.addVehicle(scenario[v]);
            new Thread(scenario[v]).start();

        }

    }

}
