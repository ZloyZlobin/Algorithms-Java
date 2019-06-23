package BinarySearchTrees;

import edu.princeton.cs.algs4.Queue;

import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value>
{
    private Node root;

    private class Node
    {
        private Key key;
        private Value val;
        private Node left, right;
        private int count;

        private Node(Key key, Value val)
        {
            this.key = key;
            this.val = val;
        }
    }

    public Value get(Key key)
    {
        Node x = root;
        while (x != null)
        {
            int cmp = key.compareTo(x.key);
            if (cmp < 0 ) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.val;
        }

        return null;
    }

    public void put(Key key, Value val)
    {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val)
    {
        if (x == null) return new Node(key, val);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Key floor(Key key)
    {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key)
    {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);

        if (cmp == 0) return x;

        if (cmp < 0) return floor(x.left, key);

        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    public int size()
    {
        return size(root);
    }

    private int size(Node x)
    {
        if (x == null) return 0;
        return x.count;
    }

    public int rank(Key key)
    {
        return rank(key, root);
    }

    private int rank(Key key, Node x)
    {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);

        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    public Iterable<Key> keys()
    {
        Queue<Key>  q = new Queue<>();
        inorder(root, q);
        return q;
    }

    private void inorder(Node x, Queue<Key> q)
    {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }

    public void deleteMin()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        root = deleteMin(root);
    }

    private Node deleteMin(Node x)
    {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void deleteMax()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        root = deleteMax(root);
    }

    private Node deleteMax(Node x)
    {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void delete(Key key)
    {
        if (key == null)
            throw new IllegalArgumentException();
        delete(root, key);
    }

    private Node delete(Node x, Key key)
    {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        if (cmp > 0) x.right = delete(x.right, key);
        else
        {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }

        x.count = size(x.left) + size(x.right) + 1;
        return x;
    }

    public boolean isEmpty()
    {
        return root == null;
    }

    public Key min()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        return min(root).key;
    }

    private Node min(Node x)
    {
        if (x.left == null) return x;
        else return min(x.left);
    }

    public Key max()
    {
        if(isEmpty())
            throw new NoSuchElementException();
        else return max(root).key;
    }

    private Node max(Node x)
    {
        if (x.right == null) return x;
        else return max(x.right);
    }
}
