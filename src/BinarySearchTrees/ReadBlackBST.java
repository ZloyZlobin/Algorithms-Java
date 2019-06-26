package BinarySearchTrees;

import java.util.NoSuchElementException;

public class ReadBlackBST<Key extends Comparable<Key>, Value>
{
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node
    {
        private Key key;
        private Value val;
        private Node left, right;
        private int count;
        private boolean color;

        private Node(Key key, Value val, boolean color)
        {
            this.key = key;
            this.val = val;
            this.color = color;
        }

        public Value get(Key key)
        {
            Node x = root;
            while (x != null)
            {
                int cmp = key.compareTo(x.key);
                if (cmp < 0) x = x.left;
                else if (cmp > 0) x = x.right;
                else return x.val;
            }
            return null;
        }

        public void put(Key key, Value val)
        {
            root = put(root, key, val);
        }

        private Node put(Node h, Key key, Value val)
        {
            if (h == null) return new Node(key, val, RED);
            int cmp = key.compareTo(h.key);
            if (cmp < 0) h.left = put(h.left, key, val);
            else if (cmp > 0) h.right = put(h.right, key, val);
            else h.val = val;

            return balance(h);
        }

        public Key floor(Key key)
        {
            if (key == null)
                throw new IllegalArgumentException();
            if (isEmpty())
                throw new NoSuchElementException();
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

        public Key ceiling(Key key)
        {
            if (key == null)
                throw new IllegalArgumentException();
            if (isEmpty())
                throw new NoSuchElementException();
            Node x = ceiling(root, key);
            if (x == null) return null;
            return x.key;
        }

        private Node ceiling(Node x, Key key)
        {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);

            if (cmp == 0) return x;
            if (cmp > 0) return ceiling(x.right, key);

            Node t = ceiling(x.left, key);
            if (t != null) return t;
            else return x;
        }

        public Key select(int k)
        {
            if (k < 0 || k >= size())
                throw new IllegalArgumentException();
            Node x = selecct(root, k);
            return x.key;
        }

        private Node selecct(Node x, int k)
        {
            int t = size(x.left);
            if (t > k) return selecct(x.left, k);
            else if (t < k) return selecct(x.right, k - t - 1);
            else return x;
        }

        public int rank(Key key)
        {
            if (key == null)
                throw new IllegalArgumentException();

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
            if (isEmpty())
                throw new NoSuchElementException();
            return max(root).key;
        }

        private Node max(Node x)
        {
            if (x.right == null) return x;
            else return max(x.right);
        }

        private boolean isRed(Node x)
        {
            if (x == null) return false;
            return x.color == RED;
        }

        private Node rotateLeft(Node h)
        {
            Node x = h.right;
            h.right = x.left;
            x.left = h;
            x.color = h.color;
            h.color = RED;
            return x;
        }

        private Node rotateRight(Node h)
        {
            Node x = h.left;
            h.left = x.right;
            x.right = h;
            x.color = h.color;
            h.color = RED;
            return x;
        }

        private void flipColors(Node h)
        {
            h.color = RED;
            h.left.color = BLACK;
            h.right.color = BLACK;
        }

        private int size(Node x)
        {
            if (x == null) return 0;
            return x.count;
        }

        public int size()
        {
            return size(root);
        }

        public boolean isEmpty()
        {
            return root == null;
        }

        public void deleteMin()
        {
            if (isEmpty())
                throw new NoSuchElementException();

            if (!isRed(root.left) && !isRed(root.right))
                root.color = RED;

            root = deleteMin(root);

            if (!isEmpty())
                root.color = BLACK;
        }

        private Node deleteMin(Node h)
        {
            if (h.left == null)
                return null;

            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);

            h.left = deleteMin(h.left);
            return balance(h);
        }

        public void deleteMax()
        {
            if (isEmpty())
                throw new NoSuchElementException();

            if (!isRed(root.left) && !isRed(root.right))
                root.color = RED;

            root = deleteMax(root);
            if (!isEmpty())
                root.color = BLACK;
        }

        private Node deleteMax(Node h)
        {
            if (h.right == null)
                return null;

            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);

            h.right = deleteMax(h.right);

            return balance(h);
        }

        public void delete(Key key)
        {
            if (key == null)
                throw new IllegalArgumentException();
            if (!contains(key))
                return;

            if (!isRed(root.left) && !isRed(root.right))
                root.color = RED;

            root = delete(root, key);
            if (!isEmpty()) root.color = BLACK;
        }

        public boolean contains(Key key)
        {
            return get(key) != null;
        }

        private Node delete(Node h, Key key)
        {
            if (key.compareTo(h.key) < 0)
            {
                if (!isRed(h.left) && !isRed(h.left.left))
                    h = moveRedLeft(h);
                h.left = delete(h.left, key);
            }
            else {
                if (isRed(h.left))
                    h = rotateRight(h);
                if (key.compareTo(h.key) == 0 && (h.right == null))
                    return null;
                if (!isRed(h.right) && !isRed(h.right.left))
                    h = moveRedRight(h);
                if (key.compareTo(h.key) == 0)
                {
                    Node x = min(h.right);
                    h.key = x.key;
                    h.val = x.val;
                    h.right = deleteMin(h.right);
                }
                else {
                    h.right = delete(h.right, key);
                }
            }
            return balance(h);
        }

        private Node moveRedLeft(Node h)
        {
            flipColors(h);
            if (isRed(h.right.left))
            {
                h.right = rotateRight(h.right);
                h = rotateLeft(h);
                flipColors(h);
            }
            return h;
        }

        private Node moveRedRight(Node h)
        {
            flipColors(h);
            if (isRed(h.left.left))
            {
                h = rotateRight(h);
                flipColors(h);
            }
            return h;
        }

        private Node balance(Node h)
        {
            if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
            if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
            if (isRed(h.left) && isRed(h.right)) flipColors(h);

            h.count = size(h.left) + size(h.right) + 1;
            return h;
        }

    }

}
