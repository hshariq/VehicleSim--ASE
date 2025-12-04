
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * 
 * Identifies the sub-path shared by two paths (i.e. path intersection).
 * 
 * @author MEZZO Inc.
 * 
 */

public class PathIntersection {

    List<int[]> pi, pj, shared;

    public PathIntersection(List<int[]> path_i, List<int[]> path_j) {

        this.pi = path_i;
        this.pj = path_j;
        identifySharedPath();

    }

    public List<int[]> getSharedPath() {
        return shared;
    }

    private void identifySharedPath() {
        shared = new ArrayList<>();

        for (ListIterator<int[]> iti = pi.listIterator(); iti.hasNext();) {
            int[] si = iti.next();
            // first 2,3
            // second 2,4
            // third 2,5
            for (ListIterator<int[]> itj = pj.listIterator(); itj.hasNext();) {
                int[] sj = itj.next();
                // f:
                // 2,6 2,5 not equal
                // 2,4 equal
                // second:
                // 2,6 2,5 not equal
                // 2,4 equal
                // third:
                // 2,6 2,4 not equal
                // 2,5 equal
                if (Arrays.equals(si, sj)) {
                    // add 2,4
                    // add 2,5
                    shared.add(si);
                    break;
                }
            }

        }

    }

    public int getSharedPathLength() {

        return shared.size();

    }

    // we remove the main method used for testing
    // public static void main(String[] args) {

    // // testing

    // List<int[]> path_i = new ArrayList<int[]>();
    // List<int[]> path_j = new ArrayList<int[]>();

    // int[] i23 = { 2, 3 };
    // path_i.add(i23);
    // int[] i24 = { 2, 4 };
    // path_i.add(i24);
    // int[] i25 = { 2, 5 };
    // path_i.add(i25);

    // int[] j26 = { 2, 6 };
    // path_j.add(j26);
    // int[] j25 = { 2, 5 };
    // path_j.add(j25);
    // int[] j24 = { 2, 4 };
    // path_j.add(j24);

    // PathIntersection id = new PathIntersection(path_i, path_j);
    // System.out.println("shared path length: " + id.getSharedPathLength());
    // System.out.println("should be: 2");

    // System.out.println();

    // List<int[]> sp = id.getSharedPath();
    // String sps = "";
    // for (int i = 0; i < sp.size(); i++) {
    // int[] p = sp.get(i);
    // sps = sps + "(" + p[0] + "," + p[1] + ") ";
    // }
    // System.out.println("shared path: " + sps);
    // System.out.println("should be: (2,4) (2,5)");

    // }

}
