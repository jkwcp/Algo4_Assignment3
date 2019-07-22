import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;


public class BruteCollinearPoints {
    private int N;

    //use a linked-list to hold lines because do not know how many lines are there
    private Node first;

    private class Node {
        LineSegment line;
        Node next;
    }

    //finds all line segment containing 4 points
    public BruteCollinearPoints (Point[] points) {
        N = 0;
        Point[] copy = invalidePointsArray(points);
        if (copy == null) throw new IllegalArgumentException();

        int length = copy.length;

        Arrays.sort(copy);

        for (int i = 0; i < length - 3; i++) {
            double slopeTemp = copy[i].slopeTo(copy[i + 1]);
            for (int j = i + 1; j < length - 2; j++) {
                if (copy[j].slopeTo(copy[j + 1]) == slopeTemp)  {
                    for (int k = j + 1; k < length - 1; k++) {
                        if (copy[k].slopeTo(copy[k + 1]) == slopeTemp) {
                            addLine(copy[i], copy[k + 1]);
                            N++;
                        }
                    }
                }
            }
        }


    }


    public int numberOfSegments() {
        return N;
    }

    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[N];
        Node temp = first;
        for (int i = 0; i < N; i++) {
            lines[i] = temp.line;
            temp = temp.next;
        }

        return lines;
    }

    private Point[] invalidePointsArray(Point[] points) {
        if (points == null) return null;
        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) return null;

            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == points[i]) return null;
                copy[i] = points[i];
            }
        }

        return copy;

    }

    private void addLine(Point a, Point b) {
        if (first == null) {
            first = new Node();
            first.next = null;
            first.line = new LineSegment(a, b);
        } else {
            Node temp = first;
            first = new Node();
            first.next = temp;
            first.line = new LineSegment(a, b);
        }
    }

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
