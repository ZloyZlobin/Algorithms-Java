package Queue;

import Stack.ArrayStack;

public class DoubleStackQueue<Item>
{
    private ArrayStack<Item> stack;
    private ArrayStack<Item> reverseStack;

    public DoubleStackQueue()
    {
        stack = new ArrayStack<>();
        reverseStack = new ArrayStack<>();
    }

    public boolean isEmpty()
    {
        return reverseStack.isEmpty() && stack.isEmpty();
    }

    public int size()
    {
        return reverseStack.size() + stack.size();
    }

    public void enqueue(Item item)
    {
        stack.push(item);
    }

    public Item dequeue()
    {
        if(reverseStack.isEmpty())
        {
            while(!stack.isEmpty())
            {
                reverseStack.push(stack.pop());
            }
        }

        return reverseStack.pop();
    }

    public static void main(String[] args)
    {
        DoubleStackQueue<String> queue = new DoubleStackQueue<>();
        queue.enqueue("test");
        queue.enqueue("to");
        queue.enqueue("be");
        queue.enqueue("or");
        queue.enqueue("not");
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
