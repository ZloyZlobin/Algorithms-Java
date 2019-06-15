package Sorting;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;
import java.util.Random;

public class Quick
{
    private static final int CUTOFF = 10;

    public static void sort(Comparable[] a)
    {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    public static void threeWaySort(Comparable[] a)
    {
        StdRandom.shuffle(a);
        threeWaySort(a, 0, a.length - 1);
    }

    public static Comparable select(Comparable[] a, int k)
    {
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (lo < hi)
        {
            int j = partition(a, lo, hi);
            if (j < k) lo = j + 1;
            else if (j > k) hi = j - 1;
            else return a[k];
        }
        return a[k];
    }

    private static void threeWaySort(Comparable[] a, int lo, int hi)
    {
        if ( hi <= lo) return;
        int lt = lo, gt = hi;
        Comparable v = a[lo];
        int i = lo + 1;
        while (i <= gt)
        {
          int cmp = a[i].compareTo(v);
          if (cmp < 0) exch(a, lt++, i++);
          else if (cmp > 0) exch(a, i, gt--);
          else i++;
        }

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static void sort(Comparable[] a, int lo, int hi)
    {
        if (hi <= lo + CUTOFF - 1)
        {
            Insertion.sort(a, lo, hi);
            return;
        }

        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi)
    {
        int i = lo, j = hi + 1;
        while (true)
        {
            while (less(a[++i], a[lo]))
            {
                if (i == hi)
                    break;
            }

            while (less(a[lo], a[--j]))
            {
                if (j == lo)
                    break;
            }

            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static void exch(Object[] a, int i, int j)
    {
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static boolean less(Comparable a, Comparable b)
    {
        return a.compareTo(b) < 0;
    }

    public static void main(String[] args)
    {
        int length = Integer.parseInt(args[0]);
        Integer[] a = new Integer[length];
        Random rnd = new Random();
        for(int i = 0; i < length; i++)
        {
            a[i] = rnd.nextInt(100);
        }

        sort(a);

        for(int i = 1; i < length; i++)
        {
            if(less(a[i], a[i - 1]))
            {
                System.out.println("Error");
            }
        }
    }
}
