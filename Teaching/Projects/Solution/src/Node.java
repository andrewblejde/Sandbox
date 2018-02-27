class Node {

    /**
     * Data to be stored by this Node. This is defined as a
     * general Object as you can choose what you want your
     * Queue to store. Remember, every class you create is a child
     * of the Object class, so you can set this object equal to a
     * String, a Page, etc.
     */
    private Object data;

    /**
     * This will store a reference to the next Node in the Queue.
     */
    private Node next;

    /**
     * This will store a reference to the previous Node in the Queue.
     */
    private Node prev;

    /**
     * Create a Node object and set its data element
     * equal to the given parameter
     * @param obj to be stored as this Nodes data
     */
    public Node(Object obj)
    {
        this.data = obj;
    }

    /**
     * Set the reference to the next Node in our Queue
     * @param next reference to be set for next Node
     */
    public void setNext(Node next)
    {
        this.next = next;
    }

    /**
     * Set the reference to the previous Node in our Queue
     * @param prev reference to be set for previous Node
     */
    public void setPrev(Node prev)
    {
        this.prev = prev;
    }

    /**
     * Get and return the reference to the next Node in our Queue
     * @return Node - reference to the next Node
     */
    public Node getNext()
    {
        return this.next;
    }

    /**
     * Get and return the reference to the previous Node in our Queue
     * @return Node - reference to the previous Node
     */
    public Node getPrev()
    {
        return this.prev;
    }

    /**
     * Get and return the data element of this NOde
     * @return Object - to be cast by the student
     */
    public Object getData()
    {
        return this.data;
    }

}