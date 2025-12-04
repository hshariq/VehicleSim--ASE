import java.util.List;

//we start our implemtnetatin with the interface
public interface PathFinder {
    List<int[]> calculatePath(Grid grid, int startX, int startY, int destX, int destY);
}
