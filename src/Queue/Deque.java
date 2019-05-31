package Queue;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // minimum size of array
    private static final int MIN_SIZE  = 2;

    // Items array
    private Item[] items;

    // head index
    private int head;

    // tail index
    private int tail;

    public Deque()
    {
        items = (Item[]) new Object[MIN_SIZE];
        head = 0;
        tail = 1;
    }

    // is deque empty
    public boolean isEmpty()
    {
        return size() == 0;
    }

    // size of deque
    public int size()
    {
        if (head < tail)
        {
            return tail - head - 1;
        }
        else
        {
            return items.length - head - 1 + tail;
        }
    }

    // add first element
    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }

        items[head] = item;
        if (head == 0)
        {
            head = items.length -1;
        }
        else {
            head--;
        }

        if (head == tail)
        {
            resize(items.length * 2);
        }
    }

    // add last element
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }

        items[tail] = item;
        if (tail == items.length - 1)
        {
            tail = 0;
        }
        else {
            tail++;
        }

        if (head == tail)
        {
            resize(items.length * 2);
        }
    }

    // remove first element
    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        int firstIndex = (head + 1) % items.length;
        Item result = items[firstIndex];
        items[firstIndex] = null;
        if (head == items.length - 1)
        {
            head = 0;
        } else {
            head++;
        }

        if (!isEmpty() && size() < items.length / 4)
        {
            resize(items.length / 2);
        }

        return result;
    }

    // remove last element
    public Item removeLast()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }

        int lastIndex = tail - 1;
        if (lastIndex < 0)
        {
            lastIndex = items.length - 1;
        }
        Item result = items[lastIndex];
        items[lastIndex] = null;
        if (tail == 0)
        {
            tail = items.length - 1;
        } else {
            tail--;
        }

        if (!isEmpty() && size() < items.length / 4)
        {
            resize(items.length / 2);
        }

        return result;
    }

    // resize array
    private void resize(int newSize)
    {
        Item[] newItems = (Item[]) new Object[newSize];
        int index = 0;
        for (Item item: this)
        {
            newItems[index++] = item;
        }
        items = newItems;
        head = items.length - 1;
        tail = index;
    }

    // iterator
    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item>
    {
        // current index
        private int current = head;
        // size
        private final int last = tail;

        // has next item
        @Override
        public boolean hasNext() {
            return (current + 1) % items.length != last;
        }

        // get next item
        @Override
        public Item next() {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }

            Item item = items[(current + 1) % items.length];
            current = (current + 1) % items.length;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("A");
        deque.addLast("B");
        deque.addFirst("C");
        deque.addLast("D");
        for (String item: deque)
        {
            StdOut.println(item);
        }

        while (!deque.isEmpty())
        {
            StdOut.println(deque.removeLast());
        }
    }
}
