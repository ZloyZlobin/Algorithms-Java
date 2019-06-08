package Sorting;

import java.util.Comparator;
import java.util.Random;

public class Merge {

    private static void merge(Object[] a, Object[] aux, int lo, int mid, int hi, Comparator comparator)
    {
        assert isSorted(a, lo, mid, comparator);
        assert isSorted(a, mid+1, hi, comparator);

        int i = lo;
        int j = mid + 1;
        for(int k = lo; k <= hi; k++)
        {
            if(i > mid) aux[k] = a[j++];
            else if(j > hi) aux[k] = a[i++];
            else if(less(a[j], a[i], comparator)) aux[k] = a[j++];
            else aux[k] = a[i++];
        }

        assert isSorted(aux, lo, hi, comparator);
    }

    private static void sort(Object[] a, Object[] aux, int lo, int hi, Comparator comparator)
    {
        if(hi <= lo) return;
        int mid = lo + (hi - lo)/2;
        sort(aux, a, lo, mid, comparator);
        sort(aux, a, mid + 1, hi, comparator);
        if(!less(a[mid + 1], a[mid], comparator))
        {
            System.arraycopy(a, lo, aux, lo, hi - lo + 1);
            return;
        }
        merge(a, aux, lo, mid, hi, comparator);
    }

    private static boolean less(Object a, Object b, Comparator comparator)
    {
        return comparator == null ? ((Comparable)a).compareTo(b) < 0 : comparator.compare(a, b) < 0;
    }

    private static boolean isSorted(Object[] a, Comparator comparator) {
        return isSorted(a, 0, a.length - 1, comparator);
    }

    private static boolean isSorted(Object[] a, int lo, int hi, Comparator comparator) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1], comparator)) return false;
        return true;
    }

    public static void sort(Comparable[] a)
    {
        sort(a, null);
    }

    public static void sort(Object[] a, Comparator  comparator)
    {
        Object[] aux = a.clone();
        sort(aux, a, 0, a.length - 1, comparator);
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
