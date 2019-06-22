package pazzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver
{
    private Stack<Board> solution;

    private class SearchNode implements Comparable<SearchNode>
    {
        public final Board item;
        public final SearchNode parent;
        private int depth;
        private final int manhattan;

        public SearchNode(Board item, SearchNode parent)
        {
            this.item = item;
            this.parent = parent;
            this.manhattan = item.manhattan();
            if (parent != null)
            {
                depth = parent.depth + 1;
            }
        }

        @Override
        public int compareTo(SearchNode that)
        {
            return (this.manhattan + this.depth) - (that.manhattan + that.depth);
        }
    }

    public Solver(Board initial)
    {
        if (initial == null)
            throw new IllegalArgumentException();

        MinPQ<SearchNode> searchNodes = new MinPQ<>();
        searchNodes.insert(new SearchNode(initial, null));
        MinPQ<SearchNode> alternativeSearchNodes = new MinPQ<>();
        alternativeSearchNodes.insert(new SearchNode(initial.twin(), null));
        SearchNode goalNode = null;
        while (!searchNodes.isEmpty() && !alternativeSearchNodes.isEmpty())
        {
            SearchNode minNode = searchNodes.delMin();
            if (minNode.item.isGoal())
            {
                goalNode = minNode;
                break;
            }

            SearchNode alternativeMinNode = alternativeSearchNodes.delMin();
            if (alternativeMinNode.item.isGoal())
            {
                break;
            }

            for (Board neighbor: minNode.item.neighbors())
            {
                if (minNode.parent == null || !neighbor.equals(minNode.parent.item))
                {
                    searchNodes.insert(new SearchNode(neighbor, minNode));
                }
            }

            for (Board neighbor: alternativeMinNode.item.neighbors())
            {
                if (alternativeMinNode.parent == null || !neighbor.equals(alternativeMinNode.parent.item))
                {
                    alternativeSearchNodes.insert(new SearchNode(neighbor, alternativeMinNode));
                }
            }
        }

        if (goalNode != null)
        {
            solution = new Stack<>();
            SearchNode node = goalNode;
            while (node != null)
            {
                solution.push(node.item);
                node = node.parent;
            }
        }
    }

    public boolean isSolvable()
    {
        return solution != null;
    }

    public int moves()
    {
        return solution == null ? -1 : solution.size() - 1;
    }

    public Iterable<Board> solution()
    {
        return solution;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
