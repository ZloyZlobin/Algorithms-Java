package UnionFind;

public class QuickUnionUF
{
    private int[] id;
    private int[] sz;
    private int[] max;

    public QuickUnionUF(int N)
    {
        id = new int[N];
        sz = new int[N];
        max = new int[N];

        for(int i = 0; i < N; i++)
        {
            id[i] = i;
            max[i] = i;
        }
    }

    private int root(int i)
    {
        while(i != id[i])
        {
            int parent = id[id[i]];
            UpdateMax(id[i], parent);
            id[i] = parent;
            i = id[i];
        }
        return i;
    }

    private void UpdateMax(int p, int q)
    {
        if(max[p] > max[q])
        {
            max[p] = max[q];
        }
        else
        {
            max[q] = max[p];
        }
    }

    public int find(int i)
    {
       return max[i];
    }

    public void union(int p, int q)
    {
        int i = root(p);
        int j = root(q);
        if(i == j)
        return;
        if(sz[i] < sz[j])
        {
            id[i] = id[j];
            sz[j] += sz[i];
            UpdateMax(i, j);
        }
        else
        {
            id[j] = id[i];
            sz[i] += sz[j];
            UpdateMax(i, j);
        }
    }

    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }

}
