package Percolation;
/* *****************************************************************************
 *  Name:    Alexander Zlobin
 *  NetID:   ZloyZlobin
 *  Precept: P00
 *
 *  Description:  Percolation
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    // grid to track open state
    private boolean[][] grid;
    // quick union to check full status
    private final WeightedQuickUnionUF fullUf;
    // quick union to check percalation
    private final WeightedQuickUnionUF uf;
    // size of grid
    private final int size;
    // number of open sites
    private int numberOfOpenSites;

    // create percolation with n x n size
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new java.lang.IllegalArgumentException();
        }

        grid = new boolean[n][n];
        fullUf = new WeightedQuickUnionUF(n * n + 1);
        uf = new WeightedQuickUnionUF(n * n + 2);
        size = n;
        numberOfOpenSites = 0;
    }

    // grid index to union find index
    private int unionIndex(int row, int col)
    {
        return (row - 1) * size + col;
    }

    // check row and col in grid range
    private void checkRange(int row, int col)
    {
        if (row < 1 || col < 1 || row > size || col > size)
        {
            throw new java.lang.IllegalArgumentException();
        }
    }

    // is site open
    public void open(int row, int col)
    {
        checkRange(row, col);

        if (isOpen(row, col))
        {
            return;
        }

        grid[row - 1][col - 1] = true;
        numberOfOpenSites++;

        if (row > 1)
        {
            if (isOpen(row - 1, col))
            {
                fullUf.union(unionIndex(row - 1, col), unionIndex(row, col));
                uf.union(unionIndex(row - 1, col), unionIndex(row, col));
            }
        }
        else
        {
            fullUf.union(0, unionIndex(row, col));
            uf.union(0, unionIndex(row, col));
        }

        if (row < size)
        {
            if (isOpen(row + 1, col))
            {
                fullUf.union(unionIndex(row + 1, col), unionIndex(row, col));
                uf.union(unionIndex(row + 1, col), unionIndex(row, col));
            }
        }

        if (row == size)
        {
            uf.union(size * size + 1, unionIndex(row, col));
        }

        if (col > 1 && isOpen(row, col - 1))
        {
            fullUf.union(unionIndex(row, col - 1), unionIndex(row, col));
            uf.union(unionIndex(row, col - 1), unionIndex(row, col));
        }

        if (col < size && isOpen(row, col + 1))
        {
            fullUf.union(unionIndex(row, col + 1), unionIndex(row, col));
            uf.union(unionIndex(row, col + 1), unionIndex(row, col));
        }
    }

    // is site open
    public boolean isOpen(int row, int col)
    {
        checkRange(row, col);

        return grid[row - 1][col - 1];
    }

    // is site full
    public boolean isFull(int row, int col)
    {
        return isOpen(row, col) && fullUf.connected(0, unionIndex(row, col));
    }

    // number of open sites
    public int numberOfOpenSites()
    {
        return numberOfOpenSites;
    }

    // is grid percolates
    public boolean percolates()
    {
        return uf.connected(0, size * size + 1);
    }

    public static void main(String[] args)
    {
        Percolation percolation = new Percolation(4);
        percolation.open(1, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);
        percolation.open(2, 1);
        percolation.open(4, 3);
        StdOut.println(percolation.isOpen(4, 3));
        StdOut.println(percolation.isFull(4, 3));
        StdOut.println(percolation.numberOfOpenSites());
        StdOut.println(percolation.percolates());
    }
}
