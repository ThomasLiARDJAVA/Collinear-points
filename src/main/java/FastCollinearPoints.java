/*************************************************************************
 *  Compilation:  javac-algs4 FastCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java LineSegment.java
 *
 *   Given a point p, the following method determines
 *   whether p participates in a set of 4 or more collinear points.
 *   Think of p as the origin.
 *   - For each other point q, determine the slope it makes with p.
 *   - Sort the points according to the slopes they makes with p.
 *   - Check if any 3 (or more) adjacent points in
 *      the sorted order have equal slopes with respect to p.
 *      If so, these points, together with p, are collinear.
 *
 *************************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A faster, sorting-based solution. Remarkably, it is possible to solve the
 * problem much faster than the brute-force solution.
 *
 * @author Mincong Huang
 */
public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    /**
     * Finds all line segments containing 4 points or more points.
     */
    public FastCollinearPoints(Point[] points) {

        checkNull(points);
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDuplicate(sortedPoints);

        final int N = points.length;
        final List<LineSegment> maxLineSegments = new LinkedList<>();

        for (int i = 0; i < N; i++) {

            Point p = sortedPoints[i];
            Point[] pointsBySlope = sortedPoints.clone();
            Arrays.sort(pointsBySlope, p.slopeOrder());

            // Notice the difference between "sortedPoints" & "pointsBySlope":
            // the below points are taken from "pointsBySlope".
            int x = 1;
            while (x < N) {

                LinkedList<Point> candidates = new LinkedList<>();
                final double SLOPE_REF = p.slopeTo(pointsBySlope[x]);
                do {
                    candidates.add(pointsBySlope[x++]);
                } while (x < N && p.slopeTo(pointsBySlope[x]) == SLOPE_REF);

                // Candidates have a max line segment if ...
                // 1. Candidates are collinear: At least 4 points are located
                //    at the same line, so at least 3 without "p".
                // 2. The max line segment is created by the point "p" and the
                //    last point in candidates: so "p" must be the smallest
                //    point having this slope comparing to all candidates.
                if (candidates.size() >= 3
                        && p.compareTo(candidates.peek()) < 0) {
                    Point min = p;
                    Point max = candidates.removeLast();
                    maxLineSegments.add(new LineSegment(min, max));
                }
            }
        }
        lineSegments = maxLineSegments.toArray(new LineSegment[0]);
    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new NullPointerException("The array \"Points\" is null.");
        }
        for (Point p : points) {
            if (p == null) {
                throw new NullPointerException(
                        "The array \"Points\" contains null element.");
            }
        }
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate(s) found.");
            }
        }
    }

    /**
     * The number of line segments.
     */
    public int numberOfSegments() {
        return lineSegments.length;
    }

    /**
     * The line segments.
     */
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    /**
     * Simple client provided by Princeton University.
     */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class FastCollinearPoints {
//    private ArrayList<LineSegment> jSegments = new ArrayList<>();
//
//    public FastCollinearPoints(Point[] points) {
//        // check corner cases
//        if (points == null)
//            throw new NullPointerException();
//
//        Point[] jCopy = points.clone();
//        Arrays.sort(jCopy);
//
//        if (hasDuplicate(jCopy)) {
//            throw new IllegalArgumentException("U have duplicate points");
//        }
//
//        for (int i = 0; i < jCopy.length - 3; i++) {
//            Arrays.sort(jCopy);
//
//            // Sort the points according to the slopes they makes with p.
//            // Check if any 3 (or more) adjacent points in the sorted order
//            // have equal slopes with respect to p. If so, these points,
//            // together with p, are collinear.
//
//            Arrays.sort(jCopy, jCopy[i].slopeOrder());
//
//            for (int p = 0, first = 1, last = 2; last < jCopy.length; last++) {
//                // find last collinear to p point
//                while (last < jCopy.length
//                        && Double.compare(jCopy[p].slopeTo(jCopy[first]), jCopy[p].slopeTo(jCopy[last])) == 0) {
//                    last++;
//                }
//                // if found at least 3 elements, make segment if it's unique
//                if (last - first >= 3 && jCopy[p].compareTo(jCopy[first]) < 0) {
//                    jSegments.add(new LineSegment(jCopy[p], jCopy[last - 1]));
//                }
//                // Try to find next
//                first = last;
//            }
//        }
//        // finds all line segments containing 4 or more points
//    }
//
//    // the number of line segments
//    public int numberOfSegments() {
//        return jSegments.size();
//    }
//
//    // the line segments
//    public LineSegment[] segments() {
//        return jSegments.toArray(new LineSegment[jSegments.size()]);
//    }
//
//    // test the whole array fo duplicate points
//    private boolean hasDuplicate(Point[] points) {
//        for (int i = 0; i < points.length - 1; i++) {
//            if (points[i].compareTo(points[i + 1]) == 0) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//}