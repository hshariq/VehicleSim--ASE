
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Implements the EastFirst path-finding algorithm.
 * 
 * @author MEZZO Inc.
 * 
 */

public class NorthFirst {

    public List<int[]> findNorthFirstPath(Grid grid, int startX, int startY, int destX, int destY) {
        List<int[]> path = new ArrayList<>();
        int currentX = startX;
        int currentY = startY;
        // each step u take is added in the path
        while (currentX != destX || currentY != destY) {
            path.add(new int[] { currentX, currentY });

            // Try to move north if possible
            if (currentY > destY) {
                currentY--;
            } else if (currentX > destX) {
                currentX--; // If north is not possible, try to move west
            } else if (currentX < destX) {
                currentX++; // If west and north are not possible, move east
            } else if (currentY < destY) {
                currentY++; // If west, east and north are not possible, try to move south
            }

        }
        // final destination is added in the end
        path.add(new int[] { destX, destY }); // Add the destination as the last step
        return path;
    }

}
