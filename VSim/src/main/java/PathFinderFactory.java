//the factory where the instantiation happens

import java.util.Random;
import java.util.List;

public class PathFinderFactory {
    private final Random rnd;

    // Constructor for path finder
    public PathFinderFactory(Random rnd) {
        this.rnd = rnd;
    }

    // factory method to create different path finders
    public PathFinder createPathFinder() {
        // random selection between 4 paths
        int choice = rnd.nextInt(4);

        switch (choice) {
            case 0:
                return new NorthFirst();
            case 1:
                return new EastFirst();
            case 2:
                return new SouthFirst();
            case 3:
                return new WestFirst();
            default:
                return new NorthFirst();
        }

    }
}