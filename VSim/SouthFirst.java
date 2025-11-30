
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Implements the SouthFirst path-finding algorithm.
 * 
 * @author MEZZO Inc.
 * 
 */

public class SouthFirst {

    public List<int[]> findSouthFirstPath(Grid grid, int startX, int startY, int destX, int destY) {
        List<int[]> path = new ArrayList<>();
        int currentX = startX;
        int currentY = startY;

        while (currentX != destX || currentY != destY) {
            path.add(new int[] { currentX, currentY });

            // Try to move south if possible
            if (currentY < destY) {
                currentY++;
            } else if (currentX > destX) {
                currentX--; // If south is not possible, try to move west
            } else if (currentY > destY) {
                currentY--; // If west and south are not possible, try to move north
            } else if (currentX < destX) {
                currentX++; // If west, north, and south are not possible, move east
            }
        }

        path.add(new int[] { destX, destY }); // Add the destination as the last step
        return path;
    }

}
