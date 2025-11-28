
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
        JFrame frame = new JFrame("Vehicle Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        int rows = 20;
        int cols = 20;
        int cellSize = 30;
        Grid grid = new Grid(rows, cols);

        JTextArea logArea = new JTextArea(10, 40);
        logArea.setEditable(false);

        GridPanel gridPanel = new GridPanel(cellSize, logArea, grid);
        frame.add(gridPanel, BorderLayout.CENTER);

        Simulation sim = new Simulation(gridPanel);
        sim.setLogger(new Logger(logArea));

        JPanel controlPanel = new JPanel();
        JTextField startXField = new JTextField(3);
        JTextField startYField = new JTextField(3);
        JTextField destXField = new JTextField(3);
        JTextField destYField = new JTextField(3);
        JTextField timePerSquareField = new JTextField(3);

        JButton addButton = new JButton("Add Vehicle");
        addButton.addActionListener(e -> {
            int startX = Integer.parseInt(startXField.getText());
            int startY = Integer.parseInt(startYField.getText());
            int destX = Integer.parseInt(destXField.getText());
            int destY = Integer.parseInt(destYField.getText());
            int timePerSquare = Integer.parseInt(timePerSquareField.getText());


            Vehicle vehicle = new Vehicle(startX, startY, destX, destY, timePerSquare, new NorthFirst(), sim);
            sim.addVehicle(vehicle);
            new Thread(vehicle).start();
        });

        JButton startPauseButton = new JButton("Start/Pause");
        startPauseButton.addActionListener(e -> {
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

        GridScenarioGenerator scengen = new GridScenarioGenerator(42, sim);


        Vehicle[] scenario = scengen.generateScenario(25, 1);


        for(int v=0; v< scenario.length;v++){

            sim.addVehicle(scenario[v]);
            new Thread(scenario[v]).start();

        }

    }

}
