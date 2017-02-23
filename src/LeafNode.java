/**
 * a leaf node contains two entries: left KVPair and right KVPair
 * @author Shuaicheng Zhang
 * @version 10/3/2016
 */
public class LeafNode implements BaseNode {
    
    private KVPair left;
    private KVPair right;
    private LeafNode leftSib;
    private LeafNode rightSib;
    
    /**
     * initialize two null pointers
     */
    public LeafNode() {
        
        left = null; 
        right = null;
        leftSib = null;
        rightSib = null;
    }

    /**
     * @param inputPair is the input KVPair
     * set left entry equal to inputPair
     */
    public void setLeft(KVPair inputPair) {
        
        left = inputPair;
    }

    /**
     * @param inputPair is the input KVPair
     *  set right entry equal to inputPair
     */
    public void setRight(KVPair inputPair) {
        
        right = inputPair;
    }

    /**
     * @return left KVPair
     */
    public KVPair getLeft() {
        
        return left;
    }

    /**
     * @return right KVPair
     */
    public KVPair getRight() {
        
        return right;
    }

    /**
     * @return true if left entry is empty
     */
    public boolean isEmpty() {
        
        return left == null;
    }

    /**
     * @return true if the right entry is not null
     */
    public boolean isFull() {
        
        return right != null;
    }
    
    /**
     * 
     * @param leftNode is the input leftnode
     */
    public void setLeftSib(LeafNode leftNode) {
        
        leftSib = leftNode;
    }
    
    /**
     * 
     * @param rightNode is the input rightnode
     */
    public void setRightSib(LeafNode rightNode) {
        
        rightSib = rightNode;
    }
    
    /**
     * 
     * @return left sibling
     */
    public LeafNode getLeftSib() {
        
        return leftSib;
    }
    
    /**
     * 
     * @return right sibling
     */
    public LeafNode getRightSib() {
        
        return rightSib;
    }

    /**
     * @return values of the left and right KVPAIRs
     */
    public String toString() {
        
        if (isEmpty()) {
            
            return "";
        }
        return left.toString() + " " + right.toString();
    }

    
    /**
     * @param node is the input node
     * @param pair is the input kvpair
     * @return updated node
     */
    public BaseNode insert(BaseNode node, KVPair pair) {
        
        if (node.isEmpty()) {
            
            node.setLeft(pair);
            return node;
        }
        BaseNode leaf = new LeafNode();
        leaf.setLeft(pair);
        return node.add(leaf);
    }
    
    
    /**
     * @param node is the input node
     * @return updated node
     */
    public BaseNode add(BaseNode node) {
        
        BaseNode internal = new InternalNode();
        if (!isFull()) {
            
            if (node.getLeft().compareTo(getLeft()) < 0) {
                
                setRight(getLeft());
                setLeft(node.getLeft());
            }
            else {
                
                setRight(node.getLeft());
            }
            
            return this;
        }    //full
        else if (node.getLeft().compareTo(getLeft()) < 0) {
            
            internal.setLeft(getLeft());
            node.setRightSib(getRightSib());
            if (getRightSib() != null) {
                getRightSib().setLeftSib(node);
            }
            setRightSib(node);
            node.setRight(getRight());
            setRight(node.getLeft());
            node.setLeft(getLeft());
            node.setLeftSib(this); 
            
            setRight(null);
            ((InternalNode) internal).setLeftChild(this);
            ((InternalNode) internal).setMiddleChild(node); 
        }
        else if (node.getLeft().compareTo(getRight()) < 0) {

            internal.setLeft(node.getLeft());
            node.setRight(getRight());
            this.setRight(null);
            node.setRightSib(getRightSib());
            if (getRightSib() != null) {
                getRightSib().setLeftSib(node);
            }
            setRightSib(node);
            node.setLeftSib(this);
            ((InternalNode) internal).setLeftChild(this);
            ((InternalNode) internal).setMiddleChild(node);
        }
        else {
            node.setRight(node.getLeft());
            node.setLeft(this.getRight());
            setRight(null);
            internal.setLeft(node.getLeft());
            node.setRightSib(getRightSib());
            if (getRightSib() != null) {
                getRightSib().setLeftSib(node);
            }
            ((LeafNode) node).setLeftSib(this);
            this.setRightSib((LeafNode) node);
            ((InternalNode) internal).setLeftChild(this);
            ((InternalNode) internal).setMiddleChild(node);
        }
        return internal;
    }

    /**
     * @param node is the input node
     */
    public void setRightSib(BaseNode node) {
        
        rightSib = (LeafNode) node;
    }

    /**
     * @param node is the input node
     */
    public void setLeftSib(BaseNode node) {
        
        leftSib = (LeafNode) node;
    }

    /**
     * @param node is the input node
     * @param pair is the input pair
     * @return updated deleted BaseNode
     */
    public BaseNode delete(BaseNode node, KVPair pair) {
        
        if (node.getLeft().compareTo(pair) == 0) {
            
            node.setLeft(null);
            if (node.getRight() != null) {
                
                node.setLeft(node.getRight());
                node.setRight(null);
            }
            else {
                
                return node;
            }
        }
        else {
           
            node.setRight(null);
        }
        return node;
    }
}
