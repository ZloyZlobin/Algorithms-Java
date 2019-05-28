package Stack;

import java.security.InvalidParameterException;
import java.util.Iterator;

public class ArrayStack<Item> implements Iterable<Item>
{
    private Item[] items;
    private int head;

    private static final int MIN_CAPACITY = 2;

    public ArrayStack()
    {
        this(MIN_CAPACITY);
    }

    public ArrayStack(int capacity)
    {
        if(capacity <= 0)
        {
            throw new InvalidParameterException();
        }

        items = (Item[])new Object[capacity];
    }

    public boolean isEmpty()
    {
        return head == 0;
    }

    public int size()
    {
        return head;
    }

    public Item pop()
    {
        if(isEmpty())
        {
            throw new UnsupportedOperationException("Stack is empty");
        }

        Item result = items[--head];
        items[head] = null;
        if(head > 0 && head == items.length / 4)
        {
            resize(items.length / 2);
        }

        return result;
    }

    public void push(Item item)
    {
        if(head == items.length)
        {
            resize(items.length * 2);
        }

        items[head++] = item;
    }

    private void resize(int newSize)
    {
        Item[] copy = (Item[]) new Object[newSize];
        for(int i = 0; i < head; i++)
        {
            copy[i] = items[i];
        }
        items = copy;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item>
    {
        private int current = head;

        @Override
        public boolean hasNext() {
            return current > 0;
        }

        @Override
        public Item next() {
            return items[--current];
        }
    }

    public static void main(String[] args)
    {
        ArrayStack<String> stack = new ArrayStack<>();
        stack.push("be");
        stack.push("to");
        stack.push("not");
        stack.push("or");
        stack.push("be");
        stack.push("test");
        stack.pop();
        stack.push("to");
        System.out.println(stack.size());
        while(!stack.isEmpty())
        {
            System.out.print(stack.pop());
            System.out.print(" ");
        }

        System.out.println();

        stack.push("1");
        stack.push("2");
        stack.push("3");
        for(String s: stack)
        {
            System.out.print(s + " ");
        }
    }
}
