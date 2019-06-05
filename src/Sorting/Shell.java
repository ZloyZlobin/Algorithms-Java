package Sorting;

import java.util.Random;

public class Shell {
    public static void Sort(Comparable[] a)
    {
        int h = 1;
        while(h < h/3)
        {
            h = 3*h + 1;
        }

        while (h >= 1)
        {
            for (int i = 0; i < a.length; i++)
            {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
                {
                    exch(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    private static void exch(Comparable[] a, int from, int to)
    {
        Comparable tmp = a[from];
        a[from] = a[to];
        a[to] = tmp;
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
            a[i] = rnd.nextInt();
        }

        Sort(a);

        for(int i = 1; i < length; i++)
        {
            if(!less(a[i - 1], a[i]))
            {
                System.out.println("Error");
            }
        }
    }
}
