
import java.util.ArrayList;
import java.util.List;


/**
* 
* Implements the SouthFirst path-finding algorithm. 
* 
* @author MEZZO Inc.
* 
*/

public class WestFirst {

    
    public List<int[]> findWestFirstPath(int startX, int startY, int destX, int destY, Grid grid) {
        List<int[]> path = new ArrayList<>();
        int currentX = startX;
        int currentY = startY;

        while (currentX != destX || currentY != destY) {
            path.add(new int[] {currentX, currentY});

            // Try to move west if possible
            if (currentX > destX) {
                currentX--;
            } else if (currentY > destY) {
                currentY--;  // If west is not possible, try to move north
            } else if (currentY < destY) {
                currentY++;  // If west and north are not possible, try to move south
            } else if (currentX < destX) {
                currentX++;  // If west, north, and south are not possible, move east
            }
        }

        path.add(new int[] {destX, destY});  // Add the destination as the last step
        return path;
    }



}
