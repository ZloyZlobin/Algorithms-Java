package pazzle;

import edu.princeton.cs.algs4.Queue;

public class Board
{
    private final int[][] blocks;
    private final int dimension;
    private int manhattan;
    private int hamming;
    private int blankRow;
    private int blankCol;

    public Board(int[][] blocks)
    {
        dimension = blocks.length;
        this.blocks = new int[dimension][];

        for (int i = 0; i < blocks.length; i++)
        {
            this.blocks[i] = new int[blocks[i].length];
            for (int j = 0; j < blocks[i].length; j++)
            {
                this.blocks[i][j] = blocks[i][j];
                setBlank(i, j);
                if (blocks[i][j] != 0)
                {
                    int distance = manhattanDistance(i, j);
                    manhattan += distance;
                    if (distance != 0)
                    {
                        hamming++;
                    }
                }
            }
        }
    }

    private void setBlank(int i, int j)
    {
        if (blocks[i][j] == 0)
        {
            blankRow = i;
            blankCol = j;
        }
    }

    private int manhattanDistance(int i, int j)
    {
        int value = blocks[i][j];
        int targetRow = (value - 1) / dimension;
        int targetCol = (value - 1) % dimension;
        return Math.abs(i - targetRow) + Math.abs(j - targetCol);
    }

    public int dimension()
    {
        return dimension;
    }

    public int hamming()
    {
        return hamming;
    }

    public int manhattan()
    {
        return manhattan;
    }

    public boolean isGoal()
    {
        return hamming == 0;
    }

    public Board twin()
    {
        int i = 0;
        int j = 0;
        if (blocks[i][j] == 0 || blocks[i][j + 1] == 0)
        {
            i++;
        }

        exch(i, j, i, j + 1);
        Board twin = new Board(blocks);
        exch(i, j, i, j + 1);

        return twin;
    }

    public boolean equals(Object y)
    {
        if (y == this)
            return true;
        if (y == null || y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;

        if (this.blankRow != that.blankRow || this.blankCol != that.blankCol || this.manhattan != that.manhattan
                || this.hamming != that.hamming || this.dimension != that.dimension)
            return false;

        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if (this.blocks[i][j] != that.blocks[i][j])
                    return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors()
    {
        Queue<Board> neighbors = new Queue<>();
        // left
        if (blankCol > 0)
        {
            exch(blankRow, blankCol, blankRow, blankCol - 1);
            neighbors.enqueue(new Board(blocks));
            exch(blankRow, blankCol, blankRow, blankCol - 1);
        }
        // top
        if (blankRow > 0)
        {
            exch(blankRow, blankCol, blankRow - 1, blankCol);
            neighbors.enqueue(new Board(blocks));
            exch(blankRow, blankCol, blankRow - 1, blankCol);
        }
        // right
        if (blankCol < dimension - 1)
        {
            exch(blankRow, blankCol, blankRow, blankCol + 1);
            neighbors.enqueue(new Board(blocks));
            exch(blankRow, blankCol, blankRow, blankCol + 1);
        }
        // bottom
        if (blankRow < dimension - 1)
        {
            exch(blankRow, blankCol, blankRow + 1, blankCol);
            neighbors.enqueue(new Board(blocks));
            exch(blankRow, blankCol, blankRow + 1, blankCol);
        }
        return neighbors;
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private void exch(int i, int j, int k, int m)
    {
        int tmp = blocks[i][j];
        blocks[i][j] = blocks[k][m];
        blocks[k][m] = tmp;
    }

    public static void main(String[] args) {
        // unit tests
    }
}
