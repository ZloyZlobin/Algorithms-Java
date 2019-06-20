package Heap;

import java.util.Random;

public class Heap {
    public static void sort(Comparable[] pq)
    {
        int n = pq.length;
        for (int k = n/2; k >= 1; k--)
        {
            sink(pq, k, n);
        }
        while(n > 1)
        {
            exch(pq, 1, n--);
            sink(pq, 1, n );
        }
    }

    private static void sink(Comparable[] pq, int k, int n)
    {
        while (2 * k <= n)
        {
            int j = k * 2;
            if (j < n && less(pq, j, j + 1)) j++;
            if (!less(pq, k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] pq, int i, int j)
    {
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }

    private static void exch(Comparable[] pq, int i, int j)
    {
        Comparable tmp = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = tmp;
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

        for(int i = 2; i <= length; i++)
        {
            if(less(a, i, i - 1))
            {
                System.out.println("Error");
            }
        }
    }
}
