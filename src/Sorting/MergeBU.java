package Sorting;

import Stack.ArrayStack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class MergeBU {
    private static void merge(Object[] a, Object[] aux, int lo, int mid, int hi, Comparator comparator)
    {
        assert isSorted(a, lo, mid, comparator);
        assert isSorted(a, mid+1, hi, comparator);

        System.arraycopy(a, lo, aux, lo, hi - lo + 1);

        int i = lo;
        int j = mid + 1;
        for(int k = lo; k <= hi; k++)
        {
            if(i > mid) a[k] = aux[j++];
            else if(j > hi) a[k] = aux[i++];
            else if(less(aux[j], aux[i], comparator)) a[k] = aux[j++];
            else a[k] = aux[i++];
        }

        assert isSorted(a, lo, hi, comparator);
    }

    private static boolean isSorted(Object[] a, int lo, int hi, Comparator comparator) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1], comparator)) return false;
        return true;
    }

    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, 0, a.length - 1, comparator);
    }

    private static boolean less(Object a, Object b, Comparator comparator)
    {
        return comparator == null ? ((Comparable)a).compareTo(b) < 0 : comparator.compare(a, b) < 0;
    }

    public static void sort(Comparable[] a)
    {
        sort(a, null);
    }

    public static void sort(Object[] a, Comparator comparator)
    {
        Object[] aux = a.clone();
        for(int sz = 1; sz < a.length; sz += sz)
        {
            for(int lo = 0; lo < a.length - sz; lo += sz + sz)
            {
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, a.length - 1), comparator);
            }
        }
        assert isSorted(a, comparator);
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
