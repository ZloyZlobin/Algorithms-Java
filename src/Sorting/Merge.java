package Sorting;

import java.util.Random;

public class Merge {

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi)
    {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        int i = lo;
        int j = mid + 1;
        for(int k = lo; k <= hi; k++)
        {
            if(i > mid) aux[k] = a[j++];
            else if(j > hi) aux[k] = a[i++];
            else if(less(a[j], a[i])) aux[k] = a[j++];
            else aux[k] = a[i++];
        }

        assert isSorted(aux, lo, hi);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi)
    {
        if(hi <= lo) return;
        int mid = lo + (hi - lo)/2;
        sort(aux, a, lo, mid);
        sort(aux, a, mid + 1, hi);
        if(!less(a[mid + 1], a[mid]))
        {
            System.arraycopy(a, lo, aux, lo, hi - lo + 1);
            return;
        }
        merge(a, aux, lo, mid, hi);
    }

    private static boolean less(Comparable a, Comparable b)
    {
        return a.compareTo(b) < 0;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    public static void sort(Comparable[] a)
    {
        Comparable[] aux = a.clone();
        sort(aux, a, 0, a.length - 1);
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
