package Queue;

public class MaxPQ<Key extends Comparable<Key>>
{
    private Key[] pq;
    private int n;

    public MaxPQ(int capacity)
    {
        pq = (Key[]) new Comparable[capacity + 1];
    }

    public MaxPQ()
    {
        this(1);
    }

    public boolean isEmpty()
    {
        return n  == 0;
    }

    public int size()
    {
        return n;
    }

    public void insert(Key key)
    {
        if (n == pq.length - 1)
            resize(n * 2);

        pq[n++] = key;
        swim(n);
    }

    public Key delMax()
    {
        Key max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n--] = null;
        if (n > 0 && n == ((pq.length - 1)/4))
           resize(pq.length / 2);
        return max;
    }

    private void swim(int i)
    {
        while (i > 0 && less(i/2, i))
        {
            exch(i/2, i);
            i /= 2;
        }
    }

    private void sink(int i)
    {
        while (2 * i <= n)
        {
            int j = 2 * i;
            if (j < n && less(j, j + 1)) j++;
            if (!less(i, j)) break;
            exch(i, j);
            i = j;
        }
    }

    private void resize(int capacity)
    {
        assert capacity > n;
        Key[] tmp = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= n; i++)
        {
            tmp[i] = pq[i];
        }
        pq = tmp;
    }

    private boolean less(int i, int j)
    {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j)
    {
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }
}
