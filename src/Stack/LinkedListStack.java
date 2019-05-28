package Stack;

import java.util.Iterator;

public class LinkedListStack<Item> implements Iterable<Item>
{
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class Node<Item>
    {
        public Item item;
        public Node next;
    }

    private Node<Item> first;
    private int size;

    public void push(Item item)
    {
        Node<Item> newItem = new Node<>();
        newItem.item = item;
        newItem.next = first;

        first = newItem;

        size++;
    }

    public Item pop()
    {
        if(isEmpty())
        {
            throw new UnsupportedOperationException ("Stack is empty");
        }

        Node<Item> lastFirst = first;
        first = first.next;
        size--;

        return lastFirst.item;
    }

    public boolean isEmpty()
    {
        return first == null;
    }

    public int size()
    {
        return size;
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args)
    {
        LinkedListStack<String> stack = new LinkedListStack<>();
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
