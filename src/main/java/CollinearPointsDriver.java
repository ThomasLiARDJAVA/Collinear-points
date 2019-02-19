/*************************************************************************
 *  Compilation:  javac-algs4 CollinearPointsDriver.java
 *  Execution:    java-algs4 CollinearPointsDriver <input_file>
 *  Dependencies: Point.java LineSegment.java
 *
 *   A driver program that you can use to test your program.
 *   To use from command line uncomment 
 *    <code> In in = new In(args[0]);</code>
 *   and comment out 
 *    <code> In in = new In("collinear-test-files/input10.txt"); </code>
 *   
 *   If you are running the program in Eclipse, to switch 
 *   file you are testing, change the file input. The test files
 *   are located in the collinear-test-files directory.  
 *   
 *
 *************************************************************************/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class CollinearPointsDriver {

  public static void main(String[] args) {

    // read the n points from a file
    // In in = new In(args[0]);
    In in = new In("collinear-test-files/input40.txt");
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
