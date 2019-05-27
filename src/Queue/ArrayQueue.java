package Queue;

import java.security.InvalidParameterException;

public class ArrayQueue<Item>
{
    private Item[] items;
    private int head;
    private int tail;

    private static final int MIN_CAPACITY = 2;

    public ArrayQueue()
    {
        this(MIN_CAPACITY);
    }

    public ArrayQueue(int capacity)
    {
        if(capacity <= 0)
        {
            throw new InvalidParameterException();
        }

        items = (Item[])new Object[capacity];
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public int size()
    {
        if(head > tail)
        {
            return items.length - head + tail;
        }
        else {
            return tail - head;
        }
    }

    public void enqueue(Item item)
    {
        if(size() == items.length - 1)
        {
            resize(items.length * 2);
        }

        items[tail++] = item;
        tail %= items.length;
    }

    public Item dequeue()
    {
        if(isEmpty())
        {
            throw new UnsupportedOperationException("Stack is empty");
        }

        Item result = items[head++];
        head %= items.length;
        if(!isEmpty() && size() < items.length / 4)
        {
            resize(items.length / 2);
        }
        return result;
    }

    private void resize(int newSize)
    {
        Item[] copy = (Item[]) new Object[newSize];
        int index = 0;
        int size = 0;
        for(int i = head; i < tail; i = (i + 1) % items.length)
        {
            copy[index++] = items[i];
            size++;
        }
        items = copy;
        tail = size;
        head = 0;
    }

    public static void main(String[] args)
    {
        ArrayQueue<String> queue = new ArrayQueue<>();
        queue.enqueue("test");
        queue.enqueue("to");
        queue.enqueue("be");
        queue.enqueue("or");
        queue.enqueue("no");
        queue.enqueue("to");
        queue.dequeue();
        queue.enqueue("be");
        System.out.println(queue.size());
        while(!queue.isEmpty())
        {
            System.out.print(queue.dequeue());
            System.out.print(" ");
        }
    }
}
