import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

public class PointTest {
  Point p5_7, p10_10, p1_1;
  @Before
  public void setUp() throws Exception {
    p5_7 = new Point(5,7);
    p10_10 = new Point(10,10);
    p1_1 = new Point(1,1);
  }

  @Test
  public void testSlopeToNormal() {
    assertEquals(0.6, p5_7.slopeTo(p10_10), 0.01);
  }

  @Test
  public void testSlopeToHorizontal() {
    Point p1_10 = new Point(1, 10);
    assertEquals(0.0, p1_10.slopeTo(p10_10), 0.00001);
  }

  @Test
  public void testSlopeToVertical() {
    Point p10_1 = new Point(10, 1);
    assertEquals(Double.POSITIVE_INFINITY, p10_1.slopeTo(p10_10), 0.0001);
  }

  @Test
  public void testSlopeToEqual() {
    Point p10_10_2 = new Point(10, 10);
    assertEquals(Double.NEGATIVE_INFINITY, p10_10_2.slopeTo(p10_10), 0.0001);
  }



  @Test
  public void testCompareToNegative() {
    assertTrue(p5_7.compareTo(p10_10) < 0);
  }

  @Test
  public void testCompareToNegativeHorizontal() {
    Point p1_10 = new Point(1, 10);
    assertTrue(p1_10.compareTo(p10_10) < 0);
  }

  @Test
  public void testCompareToNegativeVertical() {
    Point p10_1 = new Point(10, 1);
    assertTrue(p10_1.compareTo(p10_10) < 0);
  }

  @Test
  public void testCompareToEqual() {
    Point p10_10_2 = new Point(10, 10);
    assertTrue(p10_10_2.compareTo(p10_10) == 0);
  }

  @Test
  public void testCompareToPositive() {
    assertTrue(p10_10.compareTo(p1_1) > 0);
  }

  @Test
  public void testCompareToPositiveHorizontal() {
    Point p1_10 = new Point(1, 10);
    assertTrue(p10_10.compareTo(p1_10) > 0);
  }

  @Test
  public void testCompareToPositiveVertical() {
    Point p10_1 = new Point(10, 1);
    assertTrue(p10_10.compareTo(p10_1) > 0);
  }

  @Test
  public void testSlopeOrder() {
    Point point1 = new Point(10, 20);
    Comparator<Point> comparator = point1.slopeOrder();

    Point point2 = new Point(4, 8); //slope = 2
    Point point3 = new Point(3, 10); //slope = 1.4285
    assert comparator.compare(point2, point3) > 0;

    point2 = new Point(4, 8); //slope = 2
    point3 = new Point(2, 2); //slope = 2.25
    assert comparator.compare(point2, point3) < 0;
  }

}
