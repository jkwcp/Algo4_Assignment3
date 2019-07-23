import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private int n;
    private Node first;

    private class Node {
        LineSegment line;
        Node next;
    }
    public FastCollinearPoints(Point[] points) {
        n = 0;
        checkArrayValidation(points);


        int length = points.length;
        if (length < 4) return;
        Point[] copy = new Point[length];

        for (int i = 0; i < length; i++) {
            copy[i] = points[i];
        }

        Arrays.sort(copy);

        for (int i = 0; i < length - 3; i++) {
            Point origin = copy[i];
            int destArrayLength = length - 1;
            int destIndex = 0;
            Point[] dests = new Point[destArrayLength];
            for (int j = 0; j < length; j++) {
                if (j != i) {
                    dests[destIndex++] = copy[j];
                }
            }

            Arrays.sort(dests, origin.slopeOrder());

            int count = 0;
            Point starting = null;
            Point ending = null;

            for (int j = 0; j < destArrayLength - 1; j++) {
                if (origin.slopeTo(dests[j]) == origin.slopeTo((dests[j + 1]))) {
                    if (starting == null) {
                        if (origin.compareTo(dests[j]) < 0) {
                            starting = origin;
                        } else {
                            starting = dests[j];
                        }

                    }

                    count++;
                } else {
                    if (count >= 2 && origin.compareTo(starting) == 0) {
                        Point end = dests[j];
                        addLine(origin, end);
                        n++;
                    }
                    count = 0;
                    starting = null;
                }

            }
        }
    }

    public int numberOfSegments() {
        return n;
    }

    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[n];
        Node temp = first;
        for (int i = 0; i < n; i++) {
            lines[i] = temp.line;
            temp = temp.next;
        }

        return lines;
    }


    private void checkArrayValidation(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if(points[i] == null) throw new IllegalArgumentException();

            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) throw new IllegalArgumentException();
                if (points[j].equals(points[i])) throw new IllegalArgumentException();
            }
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
