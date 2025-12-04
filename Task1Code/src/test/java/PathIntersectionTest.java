
//import neccassary libraries
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PathIntersectionTest {

    // Add this helper method
    private boolean containsPoint(List<int[]> path, int x, int y) {
        for (int[] point : path) {
            if (point[0] == x && point[1] == y) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void partialOverlap() {
        // initialize first path-simialr to main method
        List<int[]> path1 = new ArrayList<>();
        path1.add(new int[] { 2, 3 });
        path1.add(new int[] { 2, 4 });
        path1.add(new int[] { 2, 5 });

        List<int[]> path2 = new ArrayList<>();
        path2.add(new int[] { 2, 6 });
        path2.add(new int[] { 2, 5 });
        path2.add(new int[] { 2, 4 });

        // create the object
        PathIntersection intersection = new PathIntersection(path1, path2);
        List<int[]> sharedPath = intersection.getSharedPath();
        // imporovement from previous print statements
        assertThat(sharedPath.size(), is(2));
        assertTrue(containsPoint(sharedPath, 2, 4));
        assertTrue(containsPoint(sharedPath, 2, 5));
    }

    @Test
    public void noOverlap() {
        // initialize first path-simialr to main method
        List<int[]> path1 = new ArrayList<>();
        path1.add(new int[] { 2, 3 });
        path1.add(new int[] { 2, 4 });
        path1.add(new int[] { 2, 5 });

        List<int[]> path2 = new ArrayList<>();
        path2.add(new int[] { 3, 6 });
        path2.add(new int[] { 3, 5 });
        path2.add(new int[] { 3, 4 });

        // create the object
        PathIntersection intersection = new PathIntersection(path1, path2);
        List<int[]> sharedPath = intersection.getSharedPath();
        // imporovement from previous print statements
        assertThat(sharedPath.size(), is(0));
    }

    @Test
    public void fullOverlap() {
        // initialize first path-simialr to main method
        List<int[]> path1 = new ArrayList<>();
        path1.add(new int[] { 2, 3 });
        path1.add(new int[] { 2, 4 });
        path1.add(new int[] { 2, 5 });

        List<int[]> path2 = new ArrayList<>();
        path2.add(new int[] { 2, 3 });
        path2.add(new int[] { 2, 4 });
        path2.add(new int[] { 2, 5 });

        // create the object
        PathIntersection intersection = new PathIntersection(path1, path2);
        List<int[]> sharedPath = intersection.getSharedPath();
        // imporovement from previous print statements
        assertThat(sharedPath.size(), is(3));
    }

    @Test
    public void singlePointOverlap() {
        // initialize first path-simialr to main method
        List<int[]> path1 = new ArrayList<>();
        path1.add(new int[] { 2, 3 });
        path1.add(new int[] { 2, 4 });
        path1.add(new int[] { 2, 5 });

        List<int[]> path2 = new ArrayList<>();
        path2.add(new int[] { 5, 6 });
        path2.add(new int[] { 4, 5 });
        path2.add(new int[] { 2, 5 });

        // create the object
        PathIntersection intersection = new PathIntersection(path1, path2);
        List<int[]> sharedPath = intersection.getSharedPath();
        // imporovement from previous print statements
        assertThat(sharedPath.size(), is(1));
        // Verify it's the correct point
        assertTrue(containsPoint(sharedPath, 2, 5));
    }

    @Test
    public void handlesNegative() {
        List<int[]> path1 = new ArrayList<>();
        path1.add(new int[] { -2, 3 });
        path1.add(new int[] { 2, 4 });
        path1.add(new int[] { 2, -5 });

        List<int[]> path2 = new ArrayList<>();
        path2.add(new int[] { -2, 3 });
        path2.add(new int[] { 2, 3 });
        path2.add(new int[] { 2, 5 });

        PathIntersection intersection = new PathIntersection(path1, path2);
        List<int[]> sharedPath = intersection.getSharedPath();
        assertThat(sharedPath.size(), is(1));
        assertTrue(containsPoint(sharedPath, -2, 3));
    }

    @Test
    public void handlesZero() {
        List<int[]> path1 = new ArrayList<>();
        path1.add(new int[] { 0, 0 });
        path1.add(new int[] { 0, 0 });

        List<int[]> path2 = new ArrayList<>();
        path2.add(new int[] { 0, 0 });
        path2.add(new int[] { 0, 0 });

        PathIntersection intersection = new PathIntersection(path1, path2);
        List<int[]> sharedPath = intersection.getSharedPath();
        assertThat(sharedPath.size(), is(2));
    }

    @Test
    public void emptyPaths() {
        List<int[]> path1 = new ArrayList<>();
        List<int[]> path2 = new ArrayList<>();

        PathIntersection intersection = new PathIntersection(path1, path2);
        List<int[]> sharedPath = intersection.getSharedPath();
        assertThat(sharedPath.size(), is(0));
    }

}
