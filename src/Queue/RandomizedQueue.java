package Queue;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // minimum size of array
    private static final int MIN_SIZE  = 2;

    // items
    private Item[] items;
    // head
    private int head;

    public RandomizedQueue()
    {
        items = (Item[]) new Object[MIN_SIZE];
    }

    public boolean isEmpty()
    {
        return head == 0;
    }

    public int size()
    {
        return head;
    }

    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }

        items[head++] = item;
        if (head >= items.length)
        {
            resize(items.length * 2);
        }
    }

    public Item dequeue()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniform(0, head);
        Item item = items[randomIndex];
        items[randomIndex] = items[--head];
        items[head] = null;
        if (!isEmpty() && size() < items.length / 4)
        {
            resize(items.length / 2);
        }
        return item;
    }

    public Item sample()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniform(0, head);
        return items[randomIndex];
    }

    private void resize(int newSize)
    {
        Item[] newItems = (Item[]) new Object[newSize];

        for (int i = 0; i < head; i++)
        {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item>
    {
        // current index
        private int current;

        // indexes
        private final int[] indexes;

        public RandomIterator()
        {
            indexes = new int[head];
            for (int i = 0; i < head; i++)
            {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
        }

        // has next item
        @Override
        public boolean hasNext() {
            return current != head;
        }

        // get next item
        @Override
        public Item next() {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            return items[indexes[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // tests
    }
}
