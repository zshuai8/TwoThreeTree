/**
 * 
 * @author shuaicheng zhang
 * @version 9/12/2016
 */
public class DLList {
    
    
    /**
     * 
     * @author zshuai8, @author jiaheng1
     * Node class
     */
    private class Node {
        private Node next;
        private Node previous;
        private int position;
        private int length;

        /**
         * Creates a new node with the given data
         *
         * @param inputP is the input position
         * @param inputL is the input length
         */
        private Node(int inputP, int inputL) {
            
            position = inputP;
            length = inputL;
        }

        /**
         * Sets the node after this node
         *
         * @param n
         *            the node after this one
         */
        private void setNext(Node n) {
            
            next = n;
        }

        /**
         * Sets the node before this one
         *
         * @param n
         *            the node before this one
         */
        private void setPrevious(Node n) {
            
            previous = n;
        }

        /**
         * Gets the next node
         *
         * @return the next node
         */
        private Node next() {
            return next;
        }

        /**
         * Gets the node before this one
         *
         * @return the node before this one
         */
        private Node previous() {
            return previous;
        }

        /**
         * Gets the position in the node
         *
         * @return the data in the node
         */
        private int getP() {
            return position;
        }
        
        /**
         * Gets the length in the node
         *
         * @return the data in the node
         */
        private int getL() {
            return length;
        }
        
        /**
         * 
         * @param inputP
         * @param inputL
         */
        private void set(int inputP, int inputL) {
            
            length = inputL;
            position = inputP;
        }
    }

    private int size;
    private Node head;
    private Node tail;
    private Node current;
    private int poolSize;
    /**
     * Create a new DLList object.
     * @param initSize is the initial size of the dlist
     */
    public DLList(int initSize) {
        
        init(initSize);
    }

    /**
     * Initializes the object to have the head and tail nodes
     */
    private void init(int initSize) {
        
        Node startNode;
        head = new DLList.Node(-1, -1);
        tail = new DLList.Node(-1, -1);
        startNode = new DLList.Node(0, initSize);
        head.setNext(startNode);
        startNode.setPrevious(head);
        tail.setPrevious(startNode);
        startNode.setNext(tail);
        current = startNode;
        poolSize = 0;
        size = 1;
    }
    
    /**
     * set the pool size
     * @param newSize is the max size of the pool
     */
    public void setPoolSize(int newSize) {
        
        poolSize = newSize;
    }


    /**
     * Gets the number of elements in the list
     *
     * @return the number of elements
     */
    public int size() {
        
        return size;
    }


    /***
     * 
     * @param inputNode is the input node
     */
    public void insert(Node inputNode) {
         
        Node addition = inputNode;
        current.next.setPrevious(addition);
        addition.setNext(current.next());
        current.setNext(addition);
        addition.setPrevious(current);     
        current = addition;
        size++;
    }
    
    /**
     * 
     * @param inputP is the input position
     * @param inputL is the input length
     */
    public void expandedList(int inputP, int inputL) {
        
        if (tail.previous.getL() + tail.previous.getP()  == inputP) {
            
            tail.previous.set(tail.previous.getP(), 
                    tail.previous.getL() + inputL);
        }
        else {
             
            Node expansion = new Node(inputP, inputL);
            current = tail.previous;
            insert(expansion);
        }
    }
    
    /**
     * 
     * @param inputL the input length
     * @return the best index for insertion
     * return -1 if there is no such index found
     */
    public int bestfitP(int inputL) {
        
        Node temp = head.next;
        boolean expandCheck = true;
        
        while (temp != tail) {
                   
            if (temp.getL() >= inputL + 2) {
                
                expandCheck = false;
                current = temp;
                break;
            }
            temp = temp.next();
        }
        
        if (expandCheck) {
            
            return -1;
        }
        else {
            
            while (current != tail) {
                
                if ((current.getL() >= inputL + 2) 
                    && (current.getL() < temp.getL())) {
                    
                    temp = current;
                }
                current = current.next();
            }
        }
        
        return temp.getP();
    }
    
    /**
     * 
     * @param inputP is the input position
     * @param inputL is the input length
     * precondition the current node must be able to shrink size
     */
    public void replaceForAdd(int inputP, int inputL) {
        
        current = head.next;
        while (current.getP() != inputP) {

            current = current.next;
        }
        
        if ((current.getL() - inputL - 2 == 0) && (size != 1)) {
            
            current.previous().setNext(current.next());
            current.next().setPrevious(current.previous());
            size--;
        }
        
        if ((current.getL() - inputL - 2 == 0) && (size == 1)
                && current.getP() != poolSize) {
            current.set(poolSize, 0);
        }
        else {
        
            current.set(inputP + inputL + 2, current.getL() - inputL - 2);
        }
    }  


    /**
     * 
     * @param inputP is the input position
     * @param inputL is the input length
     * precondition the current node must be able to shrink size
     */
    public void replaceForRemove(int inputP, int inputL) {
            
        current = head;
        if ((current.next.length == 0) && (size == 1)) {
            
            tail.setPrevious(head);
            head.setNext(tail);
            size--;   
        }
        
        
        while ((current.next.getP() < inputP)
                && (current.next != tail)) {

            current = current.next;
        }
        
        
        
        // to see if the part of memorypool being removed 
        // is surrounded by freeblocks
        if ((current.getL() + current.getP()  == inputP)
                || ((inputP +  2 + inputL) == current.next.getP())) {
            
            if  (current.getL() + current.getP() != inputP) {
                
                current.next.set(inputP, current.next.getL() + 2 + inputL);
            }
            else if ((inputP + 2 + inputL) != current.next.getP()) {
                
                current.set(current.getP(), current.getL() + 2 + inputL);
            }
            else {
                
                current.set(current.getP(), current.next.getL() + inputL 
                        + 2 + current.getL());
                remove(current.next().getP());
            }
        }
        else {
            
            Node newNode = new Node(inputP, inputL + 2);
            insert(newNode);
        }
    }

    /**
     * Removes the first object in the list that .equals(obj)
     *
     * @param inputP
     *            the object to remove
     * @return true if the object was found and removed
     */
    public boolean remove(int inputP) {
        
        Node removed = head.next();
        while (!removed.equals(tail)) { 
            
            if (removed.getP() == inputP) {
                removed.previous().setNext(removed.next());
                removed.next().setPrevious(removed.previous());
                size--;
                return true;
            }
            removed = removed.next();
        }
        return false;
    }
    
    @Override
    /**
     * to print to correct string
     */
    public String toString() {
        
        Node currentNode = head.next;
        StringBuilder blocks = new StringBuilder();
        while (currentNode.next != tail) {
            
            blocks.append("(" + currentNode.getP() + "," 
                + currentNode.getL() + ") -> ");
            currentNode = currentNode.next();
        }
        
        blocks.append("(" + currentNode.getP() + "," 
            + currentNode.getL() + ")");
        
        
        return blocks.toString();
    }
}