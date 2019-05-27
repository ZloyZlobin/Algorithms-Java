package Percolation;
/* *****************************************************************************
 *  Name:    Alexander Zlobin
 *  NetID:   ZloyZlobin
 *  Precept: P01
 *
 *  Description:  PercolationStats
 *
 **************************************************************************** */

import Percolation.Percolation;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{
    // confidence
    private static final double CONFIDENCE_96 = 1.96;
    // thresholds for trails
    private final double[] thresholds;
    // mean
    private final double mean;
    // standart deviation
    private final double stddev;

    // perform stats for n x n grid with trails amount
    public PercolationStats(int n, int trails)
    {
        if (n <= 0 || trails <= 0)
        {
            throw new java.lang.IllegalArgumentException();
        }

        Percolation percolation;
        thresholds = new double[trails];
        for (int i = 0; i < trails; i++)
        {
            percolation = new Percolation(n);

            while (!percolation.percolates())
            {
                int row = StdRandom.uniform(0, n) + 1;
                int col = StdRandom.uniform(0, n) + 1;
                percolation.open(row, col);
            }
            thresholds[i] = (double) percolation.numberOfOpenSites()/(n * n);
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
    }

    // mean stat
    public double mean()
    {
        return mean;
    }

    // standart deviation
    public double stddev()
    {
        return stddev;
    }

    // confidence low bound
    public double confidenceLo()
    {
        return mean - (CONFIDENCE_96 * stddev)/Math.sqrt(thresholds.length);
    }

    // confidence high bound
    public double confidenceHi()
    {
        return mean + (CONFIDENCE_96 * stddev)/Math.sqrt(thresholds.length);
    }

    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println("mean \t = " + stats.mean());
        StdOut.println("stddev \t = " + stats.stddev());
        double lo = stats.confidenceLo();
        double hi = stats.confidenceHi();
        String interval = "[" + lo + ", " + hi + "]";
        StdOut.println("95% confidence interval \t = " + interval);
    }
}
