package Queue;

public class LinkedListQueue<Item>
{
    private class Node<Item>
    {
        public Item item;
        public Node<Item> next;
    }

    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    public boolean isEmpty()
    {
        return head == null;
    }

    public int size()
    {
        return size;
    }

    public void enqueue(Item item)
    {
        Node<Item> newNode = new Node<>();
        newNode.item = item;
        newNode.next = null;

        if(isEmpty())
        {
            head = newNode;
            tail = newNode;
        }
        else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public Item dequeue()
    {
        if(isEmpty())
        {
            throw new UnsupportedOperationException("Stack is empty");
        }

        Item result = head.item;
        head = head.next;
        if(isEmpty())
        {
            tail = null;
        }
        size--;
        return result;
    }

    public static void main(String[] args)
    {
        LinkedListQueue<String> queue = new LinkedListQueue<>();
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
