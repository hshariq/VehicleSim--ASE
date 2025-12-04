
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 
 * Manages a log of messages from the simulated vehicles.
 * Supports message display in a GUI text area or saving to a file.
 * 
 * @author MEZZO Inc.
 * 
 */

public class Logger {

    JTextArea jta;
    File f;
    boolean active;
    ArrayList<Vehicle> records;

    public Logger(JTextArea textArea) {

        this.jta = textArea;
        active = true;

    }

    public Logger(File file) {

        f = file;

    }

    public void setActive(boolean active) {

        this.active = active;

    }

    public void append(String s) {

        if (active) {
            if (jta != null) {
                jta.append(s);
            }
        }

    }

    public void addRecord(Vehicle v) {
        if (!records.contains(v))
            records.add(v);
    }

    public void generateRecord() {

        try {

            PrintWriter out = new PrintWriter(f);
            for (Vehicle v : records) {

                String s = v.startX + "," + v.startY + "," + v.destX + "," + v.destY + "," + v.timePerSquare + ","
                        + v.creationTime + "," + v.startTime + "," + v.endTime;
                out.println(s);

            }
            out.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }

}
