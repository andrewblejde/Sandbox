import java.io.Serializable;

public class MyQueue {


    /**
     * This global field will always hold a reference to the
     * start of your Queue.
     */
    Node head;

    Object monitor = new Object();

    /**
     * Count to reflect the Queue size. This will be incremented as
     * Nodes are added and decremented as they are removed.
     */
    int count;

    /**
     * Add an Object to the Queue.
     * @param o - the object to be added
     */
    public void add(Object o)
    {

            Node cand = new Node(o);
            count++;

            /* If first item, we have a special case */
            if (head == null) {
                head = cand;
                head.setNext(head);
                head.setPrev(head);

                return;
            }

            /* Otherwise, insert at the end */
            head.getPrev().setNext(cand);
            cand.setNext(head);
            head.setPrev(cand);
    }

    /**
     * Remove the first element in the Queue. If your crawler uses threading,
     * you will need to make sure that this method is safe from multiple
     * threads doing remove operations at the same time.
     * @return Node - the removed Node
     */
    public Node remove()
    {
        synchronized (monitor) {
            /* Remove the first element in the Queue */
            Node cand = head;

            /* Check if the head is null */
            if(head == null)
                return null;

            /* Decrement */
            count--;

            /* Check whether we have a single list */
            if (head == head.getNext()) {
                head = null;
                return cand;
            }

            /* Update references */
            head.getPrev().setNext(head.getNext());
            head.getNext().setPrev(head.getPrev());
            head = head.getNext();

            /* Return element */
            return cand;
        }
    }

    /**
     * Return, but don't remove, the first element in the Queue
     * @return Node - the first node in the queue
     */
    public Node peek()
    {
        return head;
    }

    /**
     * Determine whether the Queue is empty or not
     * @return true if empty, false otherwise
     */
    public boolean isEmpty()
    {
        return head == null;
    }

    /**
     * Determine the size of the Queue
     * @return size - the size as int
     */
    public int size()
    {
        return this.count;
    }

}
