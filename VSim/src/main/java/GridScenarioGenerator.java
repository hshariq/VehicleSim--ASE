
import java.util.List;
import java.util.Random;

/**
 * 
 * Generates random traffic scenarios with a specific number of {@link Vehicle}
 * instances.
 * It also supports the generation of scenarios with an upper-bound on the
 * number of grid squares their paths can intersect.
 * 
 * @author MEZZO Inc.
 * 
 */

public class GridScenarioGenerator {

    Grid grid;
    Simulation sim;
    Random rnd;
    // add pathfinder attribute
    private PathFinderFactory pathfinderfactory;

    public GridScenarioGenerator(long seed, Simulation sim) {

        this.sim = sim;
        this.grid = sim.getGrid();
        rnd = new Random(seed);
        // initialize pathfinder factory
        this.pathfinderfactory = new PathFinderFactory(rnd);

    }

    /**
     * Creates the vehicles for the scenario, with random start and destination
     * points, as well as random speeds.
     * It assigns all vehicles with a {@link NorthFirst} pathfinder.
     * 
     * @param nVehicles the number of vehicles in the generated scenario
     * 
     * @return an array containing the generated vehicles.
     */

    public Vehicle[] generateScenario(int nVehicles) {
        Vehicle[] scenario = new Vehicle[nVehicles];

        for (int i = 0; i < nVehicles; i++) {

            int dimX = grid.getCols();
            int dimY = grid.getRows();
            int startX, startY, destX, destY;

            do {
                startX = rnd.nextInt((int) dimX);
                destX = rnd.nextInt((int) dimX);
                startY = rnd.nextInt((int) dimY);
                destY = rnd.nextInt((int) dimY);
            } while (startX == startY && destX == destY);

            int timePerSq = 1 + rnd.nextInt(10);
            // this is where our nrothfirst is hardcoded, we wnat it to be random
            PathFinder pathfinder = pathfinderfactory.createPathFinder();
            // print
            System.out.println("Vehicle " + i + " uses " + pathfinder.getClass().getSimpleName() + " pathfinder.");
            scenario[i] = new Vehicle(startX, startY, destX, destY, timePerSq, pathfinder, sim);

        }

        return scenario;
    }

    /**
     * Creates the vehicles for the scenario, with random start and destination
     * points, as well as random speeds.
     * It ensures the vehicle's paths intersect over at most
     * <i>maxIntersectionLength</i> grid squares.
     * 
     * 
     * @param nVehicles             the number of vehicles in the generated scenario
     * @param maxIntersectionLength the maximum allowed path intersection length in
     *                              the generated scenario
     * 
     * @return an array containing the generated vehicles.
     */

    public Vehicle[] generateScenario(int nVehicles, int maxIntersectionLength) {
        Vehicle[] scenario = new Vehicle[nVehicles];

        int i = 0;
        // new random pathfinder for first vehicle
        // we add this beofre the loop to ensure that new pathfinder is only created
        // when we add a new vehicle, so that same direction keep randomising till u
        // find a good path
        PathFinder currentPath = pathfinderfactory.createPathFinder();

        while (i < nVehicles) {

            int dimX = grid.getCols();
            int dimY = grid.getRows();
            int startX, startY, destX, destY;

            do {
                startX = rnd.nextInt((int) dimX);
                destX = rnd.nextInt((int) dimX);
                startY = rnd.nextInt((int) dimY);
                destY = rnd.nextInt((int) dimY);
            } while (startX == startY && destX == destY);

            int timePerSq = 1 + rnd.nextInt(10);

            // List<int[]> path = new NorthFirst().findNorthFirstPath(grid,startX, startY,
            List<int[]> path = currentPath.calculatePath(grid, startX, startY, destX, destY);

            if (i == 0) {

                // add first vehicle
                scenario[i] = new Vehicle(startX, startY, destX, destY, timePerSq, path, sim);
                i++;
            } else {

                // for subsequent vehicles, iterate over the paths of previously added vehicles
                boolean isGood = true;
                for (int j = 0; j < i; j++) {
                    // check if intersection between paths is smaller or equal the desired
                    // upper-bound
                    isGood = testPath(scenario[j].getPath(), path, maxIntersectionLength);
                    if (!isGood) {
                        // path intersection is larger than desired upper bound, so discard it and try
                        // again
                        break;
                    }
                }

                if (isGood) {
                    // path intersection is not larger than desired upper bound, so add new vehicle
                    // to the scenario
                    scenario[i] = new Vehicle(startX, startY, destX, destY, timePerSq, path, sim);
                    i++;

                    // adding new random pathfinder for next vehicle
                    currentPath = pathfinderfactory.createPathFinder();
                }
            }

        }

        return scenario;
    }

    public boolean testPath(List<int[]> p1, List<int[]> p2, int max) {

        PathIntersection id = new PathIntersection(p1, p2);
        int idlength = id.getSharedPathLength();
        return idlength <= max;

    }

}
