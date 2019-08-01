/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;

public class KdTree
{
    private class Node
    {
        public final Point2D point;
        public final boolean horizontal;
        public final Node parent;
        public Node left;
        public Node right;
        private RectHV rect;

        public Node(Point2D p, boolean horizontal, Node parent)
        {
            this.point = p;
            this.horizontal = horizontal;
            this.parent = parent;
        }

        public int compareByOrientationTo(Point2D other)
        {
            if (horizontal)
            {
                return Double.compare(point.x(), other.x());
            }
            else {
                return Double.compare(point.y(), other.y());
            }
        }

        private double maxY(Node node)
        {
            if (node.parent == null)
                return MAX_Y;

            if (!node.parent.horizontal && node.parent.left == node)
            {
                return node.parent.point.y();
            } else {
                return maxY(node.parent);
            }
        }

        private double maxX(Node node)
        {
            if (node.parent == null)
                return MAX_X;

            if (node.parent.horizontal && node.parent.left == node)
            {
                return node.parent.point.x();
            }
            else {
                return maxX(node.parent);
            }
        }

        private double minY(Node node)
        {
            if (node.parent == null)
                return 0;

            if (!node.parent.horizontal && node.parent.right == node)
            {
                return node.parent.point.y();
            } else {
                return minY(node.parent);
            }
        }

        private double minX(Node node)
        {
            if (node.parent == null)
                return 0;

            if (node.parent.horizontal && node.parent.right == node)
            {
                return node.parent.point.x();
            } else {
                return minX(node.parent);
            }
        }

        public RectHV rect()
        {
            if (rect == null)
                rect = new RectHV(minX(this), minY(this), maxX(this), maxY(this));
            return rect;
        }
    }

    private static final int MAX_X = 1;
    private static final int MAX_Y = 1;

    private Node rootNode;
    private int size;

    public KdTree()
    {
        size = 0;
        rootNode = null;
    }

    public boolean isEmpty()
    {
        return rootNode == null;
    }

    public int size()
    {
        return size;
    }

    public void insert(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }

        if (rootNode == null)
        {
            rootNode = new Node(p, true, null);
            size++;
        }
        else {
            insert(p, rootNode);
        }
    }

    public boolean contains(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }
        return find(p, rootNode) != null;
    }

    public void draw()
    {
        Queue<Node> nodes = new Queue<>();
        nodes.enqueue(rootNode);
        while (!nodes.isEmpty())
        {
            Node node = nodes.dequeue();
            if (node.horizontal)
            {
                StdDraw.setPenColor(Color.red);
                if (node.parent != null)
                {
                    if (node.parent.compareByOrientationTo(node.point) > 0)
                    {
                        StdDraw.line(node.point.x(), 0, node.point.x(), node.parent.point.y());
                    }
                    else {
                        StdDraw.line(node.point.x(), node.parent.point.y(), node.point.x(), MAX_Y);
                    }
                }
                else {
                    StdDraw.line(node.point.x(), 0, node.point.x(), MAX_Y);
                }
            }
            else {
                StdDraw.setPenColor(Color.blue);
                if (node.parent.compareByOrientationTo(node.point) > 0)
                {
                    StdDraw.line(0, node.point.y(), node.parent.point.x(), node.point.y());
                } else {
                    StdDraw.line(node.parent.point.x(), node.point.y(), MAX_X, node.point.y());
                }
            }
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.filledCircle(node.point.x(), node.point.y(), 0.01);
            if (node.left != null)
            {
                nodes.enqueue(node.left);
            }
            if (node.right != null)
            {
                nodes.enqueue(node.right);
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null)
        {
            throw new IllegalArgumentException();
        }

        Queue<Point2D> rangeSet = new Queue<>();
        range(rect, rootNode, rangeSet);
        return rangeSet;
    }

    private void range(RectHV rect, Node node, Queue<Point2D> points)
    {
        if (node == null)
            return;

        if (rect.contains(node.point))
        {
            points.enqueue(node.point);
        }

        if (node.horizontal)
        {
            if (rect.xmax() < node.point.x())
            {
                range(rect, node.left, points);
            }
            else {
                if (rect.xmin() <= node.point.x())
                {
                    range(rect, node.left, points);
                }
                range(rect, node.right, points);
            }
        }
        else {
            if (rect.ymax() < node.point.y())
            {
                range(rect, node.left, points);
            }
            else {
                if (rect.ymin() <= node.point.y())
                {
                    range(rect, node.left, points);
                }
                range(rect, node.right, points);
            }
        }
    }

    public Point2D nearest(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }

        if (isEmpty())
        {
            return null;
        }

        double minDistance = Double.POSITIVE_INFINITY;
        Stack<Node> nodes = new Stack<>();
        Node nearestNode = null;
        nodes.push(rootNode);
        while (!nodes.isEmpty())
        {
            Node node = nodes.pop();
            if (node.rect().distanceSquaredTo(p) >= minDistance)
            {
                continue;
            }
            double distance = p.distanceSquaredTo(node.point);
            if (distance < minDistance)
            {
                minDistance = distance;
                nearestNode = node;
            }

            double distanceLeft = node.left == null ? Double.POSITIVE_INFINITY : node.left.rect().distanceSquaredTo(p);
            double distanceRight = node.right == null ? Double.POSITIVE_INFINITY : node.right.rect().distanceSquaredTo(p);
            if (distanceLeft <= distanceRight)
            {
                if (distanceRight < minDistance)
                {
                    nodes.push(node.right);
                }
                if (distanceLeft < minDistance)
                {
                    nodes.push(node.left);
                }
            }
            else
            {
                if (distanceLeft < minDistance)
                {
                    nodes.push(node.left);
                }
                if (distanceRight < minDistance)
                {
                    nodes.push(node.right);
                }

            }
        }

        return nearestNode.point;
    }

    private Node find(Point2D p, Node node)
    {
        if (node == null)
            return null;
        if (node.point.equals(p))
            return node;
        int compareResult = node.compareByOrientationTo(p);
        if (compareResult > 0)
        {
            return find(p, node.left);
        }
        else {
            return find(p, node.right);
        }
    }

    private void insert(Point2D p, Node node)
    {
        if (p.compareTo(node.point) == 0)
            return;

        int compareResult = node.compareByOrientationTo(p);

        if (compareResult > 0)
        {
            if (node.left == null)
            {
                node.left = new Node(p, !node.horizontal, node);
                size++;
            }
            else
            {
                insert(p, node.left);
            }
        }
        else {
            if (node.right == null)
            {
                node.right = new Node(p, !node.horizontal, node);
                size++;
            }
            else {
                insert(p, node.right);
            }
        }
    }

    public static void main(String[] args)
    {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.5625, 0.40625));
        tree.insert(new Point2D(0.90625, 0.90625));
        tree.insert(new Point2D(0.71875, 0.15625));
        tree.insert(new Point2D(0.28125, 0.34375));
        tree.insert(new Point2D(0.78125, 0.875));
        tree.insert(new Point2D(0.75, 0.1875));
        tree.insert(new Point2D(0.59375, 0.03125));
        tree.insert(new Point2D(0.09375, 0.5));
        tree.insert(new Point2D(0.3125, 0.46875));
        tree.insert(new Point2D(0.4375, 0.3125));
        tree.insert(new Point2D(0.1875, 0.5625));
        tree.insert(new Point2D(0.03125, 0.59375));
        tree.insert(new Point2D(0.625, 0.53125));
        tree.insert(new Point2D(0.21875, 0.28125));
        tree.insert(new Point2D(0.875, 0.25));
        tree.insert(new Point2D(0.0625, 0.96875));
        tree.insert(new Point2D(0.46875, 0.125));
        tree.insert(new Point2D(1.0, 0.8125));
        tree.insert(new Point2D(0.84375, 0.0));
        tree.insert(new Point2D(0.34375, 0.71875));
        tree.draw();
        tree.nearest(new Point2D(0.25, 0.84375));
        StdOut.println(tree.contains(new Point2D(0.5, 0.7)));
    }
}
