/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    private final SET<Point2D> points;

    public PointSET()
    {
        points = new SET<>();
    }

    public boolean isEmpty()
    {
        return points.isEmpty();
    }

    public int size()
    {
        return points.size();
    }

    public void insert(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }

        if (!points.contains(p))
        {
            points.add(p);
        }
    }

    public boolean contains(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }

        return points.contains(p);
    }

    public void draw()
    {
        for (Point2D point: points)
        {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null)
        {
            throw new IllegalArgumentException();
        }

        Queue<Point2D> rectPoints = new Queue<>();
        for (Point2D point : points)
        {
            if (rect.contains(point))
            {
                rectPoints.enqueue(point);
            }
        }
        return rectPoints;
    }

    public Point2D nearest(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }
        if (points.isEmpty())
        {
            return null;
        }

        double minDistance = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = null;
        for (Point2D point : points)
        {
            double distance = p.distanceSquaredTo(point);
            if (distance < minDistance)
            {
                nearestPoint = point;
                minDistance = distance;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        // tests
    }
}
