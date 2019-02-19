import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segmentList;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException("points null");
        for (Point p : points)
            if (p == null) throw new java.lang.NullPointerException("some null");
        segmentList = new ArrayList<LineSegment>();
        // copy the points
        Point[] copyPoints = points.clone();
        Arrays.sort(copyPoints);
        // System.out.println(Arrays.deepToString(points));
        double slope1, slope2, slope3;
        for (int i = 0; i < copyPoints.length; i++)
            for (int j = i + 1; j < copyPoints.length; j++) {
                slope1 = copyPoints[i].slopeTo(copyPoints[j]);
                // if repeated points
                if (slope1 == Double.NEGATIVE_INFINITY)
                    throw new java.lang.IllegalArgumentException("repeat points");
                for (int k = j + 1; k < copyPoints.length; k++) {
                    slope2 = copyPoints[i].slopeTo(copyPoints[k]);
                    if (slope1 != slope2) continue;
                    for (int l = k + 1; l < copyPoints.length; l++) {
                        slope3 = copyPoints[i].slopeTo(copyPoints[l]);
                        if (slope1 == slope3)
                            segmentList.add(new LineSegment(copyPoints[i], copyPoints[l]));
                    }
                }
            }
    }
    // the number of line segments
    public int numberOfSegments() {
        return segmentList.size();
    }
    // the line segments
    public LineSegment[] segments() {
        LineSegment [] s = new LineSegment[numberOfSegments()];
        Object [] a = segmentList.toArray();
        for (int i = 0; i < s.length; i++) s[i] = (LineSegment) a[i];
        return s;
    }

    // unit test
    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
