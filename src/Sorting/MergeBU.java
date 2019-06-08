package Sorting;

import java.util.Random;

public class MergeBU {
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        System.arraycopy(a, lo, aux, lo, hi - lo + 1);

        int i = lo;
        int j = mid + 1;
        for(int k = lo; k <= hi; k++)
        {
            if(i > mid) a[k] = aux[j++];
            else if(j > hi) a[k] = aux[i++];
            else if(less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }

        assert isSorted(a, lo, hi);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean less(Comparable a, Comparable b)
    {
        return a.compareTo(b) < 0;
    }

    public static void sort(Comparable[] a)
    {
        Comparable[] aux = a.clone();
        for(int sz = 1; sz < a.length; sz += sz)
        {
            for(int lo = 0; lo < a.length - sz; lo += sz + sz)
            {
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, a.length - 1));
            }
        }
        assert isSorted(a);
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

        for(Integer i: a)
            System.out.print(i + " ");
    }
}
