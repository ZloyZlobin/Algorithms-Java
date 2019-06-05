package Sorting;

import java.util.Random;

public class Insertion {
    public static void Sort(Comparable[] a)
    {
        for(int i = 0; i < a.length; i++)
        {
            for(int j = i; j > 0; j--)
            {
                if(less(a[j], a[j - 1]))
                {
                    exch(a, j, j-1);
                }
                else {
                    break;
                }
            }
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
