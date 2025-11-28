
import java.util.ArrayList;
import java.util.List;



/**
* 
* Implements the EastFirst path-finding algorithm. 
* 
* @author MEZZO Inc.
* 
*/

public class EastFirst {

    public List<int[]> findEastFirstPath(Grid grid, int startX, int startY, int destX, int destY) {
        List<int[]> path = new ArrayList<>();
        int currentX = startX;
        int currentY = startY;

        while (currentX != destX || currentY != destY) {
            path.add(new int[] {currentX, currentY});

            // Try to move east if possible
            if (currentX > destX) {
                currentX++;
            } else if (currentY > destY) {
                currentY--;  // If east is not possible, try to move north
            } else if (currentY < destY) {
                currentY++;  // If east and north are not possible, try to move south
            } else if (currentX < destX) {
                currentX--;  // If east, north, and south are not possible, move west
            }
        }

        path.add(new int[] {destX, destY});  // Add the destination as the last step
        return path;
    }



}
