package Percolation;

import UnionFind.QuickFindUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Programm
{
    public static void main(String[] args)
    {
        int N = StdIn.readInt();
        QuickFindUF uf = new QuickFindUF(N);
        while(!StdIn.isEmpty())
        {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if(!uf.connected(p, q))
            {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
        }
    }
}
