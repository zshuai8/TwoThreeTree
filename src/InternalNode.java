/**
 * an internal node contains two entries: left KVPair and right KVPair
 * @author shuaicheng zhang
 * @version 10/3/2016
 */
public class InternalNode implements BaseNode {
    private KVPair left;
    private KVPair right;
    private BaseNode leftChild = null;
    private BaseNode rightChild = null;
    private BaseNode middleChild = null;
    private BaseNode leftSib = null;
    private BaseNode rightSib = null;
    
    /**
     * initialize two null pointers
     */
    public InternalNode() {
        left = null;
        right = null;
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
     * @param inputNode is the input BaseNode
     *  set left child equal to inputNode
     */
    public void setLeftChild(BaseNode inputNode) { 
        leftChild = inputNode;
    }
    
    /**
     * @param inputNode is the input BaseNode
     *  set right child equal to inputNode
     */
    public void setRightChild(BaseNode inputNode) {     
        rightChild = inputNode;
    }
    
    /**
     * @param inputNode is the input BaseNode
     *  set middle child equal to inputNode
     */
    public void setMiddleChild(BaseNode inputNode) {
        middleChild = inputNode;
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
     * @return left child node
     */
    public BaseNode getLeftChild() {
        return leftChild;
    }
    
    /**
     * @return middle child node
     */
    public BaseNode getMiddleChild() {
        return middleChild;
    }
    
    /**
     * @return right child node
     */
    public BaseNode getRightChild() {
        return rightChild;
    }
    
    /**
     * @return left sibling internal node
     */
    public BaseNode getLeftSib() {
        return leftSib;
    }
    
    /**
     * @return right sibling internal node
     */
    public BaseNode getRightSib() {
        return rightSib;
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
     * @return values of the left and right KVPAIRs
     */
    public String toString() {
        if (isEmpty()) {
            return "";
        }
        return left.toString() + " " + right.toString();
    }
    
    /**
     * @return updated node
     * @param node is the input node
     * @param pair is the input kvpair
     */
    public BaseNode insert(BaseNode node, KVPair pair) {
        if (node instanceof LeafNode) {
            BaseNode leaf = new LeafNode();
            leaf.setLeft(pair);
            return node.add(leaf);
        }   
        BaseNode internal;
        InternalNode currNode = (InternalNode) node;
        if (currNode.getLeft().compareTo(pair) > 0) {  
            internal = insert(currNode.getLeftChild(), pair);
            if (internal == currNode.getLeftChild()) {
                return currNode;
            }
            else {
                return currNode.add(internal);
            }
        }
        else if (currNode.getRight() == null ||
                currNode.getRight().compareTo(pair) > 0) { 
            internal = insert(currNode.getMiddleChild(), pair);
            if (internal == currNode.getMiddleChild()) {
                return currNode;
            }
            else {     
                return currNode.add(internal);
            }
        }
        else {    
            internal = insert(currNode.getRightChild(), pair);
            if (internal == currNode.getRightChild()) {      
                return currNode;
            }
            else {         
                return currNode.add(internal);
            }
        }
    }
    
    /**
     * @return updated node
     * @param node is the input node
     */
    public BaseNode add(BaseNode node) {
        
        if (!isFull()) {
            if (node.getLeft().compareTo(getLeft()) < 0) {
                setRight(getLeft());
                setLeft(node.getLeft());
                setRightChild(getMiddleChild());
                setMiddleChild(((InternalNode) node).getMiddleChild());
                setLeftChild(((InternalNode) node).getLeftChild());
            }
            else {
                setRight(node.getLeft());
                setMiddleChild(((InternalNode) node).getLeftChild());
                setRightChild(((InternalNode) node).getMiddleChild());
            }
            getLeftChild().setRightSib(getMiddleChild());
            getMiddleChild().setLeftSib(getLeftChild());
            getMiddleChild().setRightSib(getRightChild());
            getRightChild().setLeftSib(getMiddleChild());
            return this;
        }
        else if (node.getLeft().compareTo(getLeft()) < 0) {
            InternalNode internal = new InternalNode();
            getMiddleChild().setLeftSib(getLeftChild().getLeftSib());
            if (getLeftChild().getLeftSib() != null) {
                getLeftChild().getLeftSib().setRightSib(getMiddleChild());
            }
            setLeftChild(getMiddleChild());
            setMiddleChild(getRightChild());
            setRightChild(null);
            setRight(null);
            setLeft(updateValue(middleChild));
            internal.setLeftChild(node);
            internal.setMiddleChild(this);       
            internal.getLeftChild().setRightSib(internal.getMiddleChild());
            internal.getMiddleChild().setLeftSib(internal.getLeftChild());
            internal.setLeft(updateValue(internal.middleChild));
            return internal;
        }
        else if (node.getLeft().compareTo(getRight()) < 0) {
            
            InternalNode internal = new InternalNode();
            InternalNode currNode = (InternalNode) node;
            setRight(null);
            setMiddleChild(currNode.getLeftChild());
            currNode.setLeftChild(currNode.getMiddleChild());
            currNode.setMiddleChild(getRightChild());
            setRightChild(null);
            currNode.setLeft(updateValue(currNode.getMiddleChild()));
            currNode.getLeftChild().
                setRightSib(currNode.getMiddleChild());
            currNode.getMiddleChild().
                setLeftSib(((InternalNode) currNode).getLeftChild());
            internal.setLeftChild(this);
            internal.setMiddleChild(currNode);
            internal.getMiddleChild().setRightSib(
                    internal.getLeftChild().getRightSib());
            if (internal.getLeftChild().getRightSib() != null) {
                internal.getLeftChild().getRightSib().
                    setLeftSib(internal.getMiddleChild());
            }
            internal.getLeftChild().setRightSib(internal.getMiddleChild());
            internal.getMiddleChild().setLeftSib(internal.getLeftChild());
            internal.setLeft(updateValue(internal.middleChild));
            return internal;
        }
        else {
            InternalNode internal = new InternalNode();
            setRight(null);
            setRightChild(null);
            internal.setLeftChild(this);
            internal.setMiddleChild(node);
            internal.setLeft(updateValue(internal.getMiddleChild()));
            internal.getMiddleChild()
                .setRightSib(internal.getLeftChild().getRightSib());
            if (internal.getLeftChild().getRightSib() != null) {
                internal.getLeftChild().getRightSib()
                    .setLeftSib(internal.getMiddleChild());
            }
            internal.getLeftChild().setRightSib(internal.getMiddleChild());
            internal.getMiddleChild().setLeftSib(internal.getLeftChild());
            return internal;
        }
    }
    
    /**
     * 
     * @param node is the input node
     * @return updated kvpair
     */
    public KVPair updateValue(BaseNode node) {
        if (node == null) {
            return null;
        }
        while (!(node instanceof LeafNode)) {
            node = ((InternalNode) node).getLeftChild();
        }
        return node.getLeft();
    }
    
    /**
     * @param node is the basenode
     */
    public void setRightSib(BaseNode node) {
        rightSib = (InternalNode) node;
    }

    /**
     * @param node is the basenode
     */
    public void setLeftSib(BaseNode node) {
        leftSib = (InternalNode) node;
    }
    
    /**
     * 
     * @return the Basenode after merge
     */
    public BaseNode merge() {
        if (getLeftChild().isEmpty()) {
            if (getMiddleChild().getRight() != null) {
                if (getLeftChild() instanceof LeafNode) {
                    setLeft(getMiddleChild().getRight());
                    getLeftChild().setLeft(getMiddleChild().getLeft());
                    getMiddleChild().setLeft(
                            getMiddleChild().getRight());
                    
                }
                else {   
                    getLeftChild().setLeft(getLeft());
                    setLeft(getMiddleChild().getLeft());
                    getMiddleChild().setLeft(
                            getMiddleChild().getRight());
                    ((InternalNode) getLeftChild()).
                        setMiddleChild(((InternalNode) getMiddleChild()).
                            getLeftChild());
                    ((InternalNode) getMiddleChild()).
                        setLeftChild(((InternalNode) getMiddleChild()).
                            getMiddleChild());
                    ((InternalNode) getMiddleChild()).
                        setMiddleChild(((InternalNode) getMiddleChild()).
                            getRightChild());   
                    ((InternalNode) getMiddleChild()).setRightChild(null); 
                }
                getMiddleChild().setRight(null);
                return this;
            }
            else if (getRightChild() != null) {
                if (getLeftChild() instanceof LeafNode) {
                    getMiddleChild().setLeftSib(getLeftChild().getLeftSib());
                    if (getLeftChild().getLeftSib() != null) {
                        getLeftChild().getLeftSib().
                            setRightSib(getMiddleChild());
                    }
                    setLeft(getRightChild().getLeft());
                    setLeftChild(getMiddleChild());
                    setMiddleChild(getRightChild());
                }
                else {
                    getLeftChild().setLeft(getLeft());
                    setLeft(getRight());
                    getLeftChild().setRight(getMiddleChild().getLeft());
                    ((InternalNode) getLeftChild()).
                        setMiddleChild(((InternalNode) getMiddleChild()).
                            getLeftChild());
                    ((InternalNode) getLeftChild()).
                        setRightChild(((InternalNode) getMiddleChild()).
                                getMiddleChild());
                    ((InternalNode) getLeftChild()).getLeftChild().
                        setRightSib(((InternalNode) getLeftChild()).
                                getMiddleChild());
                    ((InternalNode) getLeftChild()).getMiddleChild().
                        setLeftSib(((InternalNode) getLeftChild()).
                                getLeftChild());
                }
                setMiddleChild(getRightChild());
                setRightChild(null);
                setRight(null);
                return this;
            }
            else {
                if (getLeftChild() instanceof LeafNode) {   
                    getMiddleChild().setLeftSib(
                            getLeftChild().getLeftSib());
                    if (getLeftChild().getLeftSib() != null) {
                        getLeftChild().getLeftSib()
                            .setRightSib(getMiddleChild());
                    }
                    setLeftChild(getMiddleChild());
                }
                else {
                    getLeftChild().setLeft(getLeft());
                    getLeftChild().setRight(getMiddleChild().getLeft());
                    getLeftChild().setRightSib(getMiddleChild().getRightSib());
                    if (getMiddleChild().getRightSib() != null) {
                        getMiddleChild().getRightSib().
                            setLeftSib(getLeftChild());
                    }
                    ((InternalNode) getLeftChild()).
                        setMiddleChild(((InternalNode) 
                                getMiddleChild()).getLeftChild());
                    ((InternalNode) getLeftChild()).
                        setRightChild(((InternalNode) getMiddleChild()).
                            getMiddleChild());
                }
                
                setMiddleChild(null);
                setLeft(null);
                return this;
            }
        } 
        else if (getMiddleChild().isEmpty()) {
            if (getLeftChild().isFull()) {   
                if (getLeftChild() instanceof LeafNode) {
                    getMiddleChild()
                        .setLeft(getLeftChild().getRight());
                    setLeft(getLeftChild().getRight());
                }
                else {                    
                    getMiddleChild().setLeft(updateValue(
                            ((InternalNode) getMiddleChild()).getLeftChild()));
                    ((InternalNode) getMiddleChild())
                        .setMiddleChild(((InternalNode) getMiddleChild())
                                .getLeftChild());
                    ((InternalNode) getMiddleChild())
                        .setLeftChild(((InternalNode) getLeftChild())
                                .getRightChild());
                    ((InternalNode) getLeftChild()).setRightChild(null);
                    setLeft(updateValue(getMiddleChild()));   
                }
                getLeftChild().setRight(null);
                return this;
            }
            else if (getRightChild() != null && getRightChild().isFull()) {    
                if (getLeftChild() instanceof LeafNode) {
                    setLeft(getRightChild().getLeft());
                    getMiddleChild().setLeft(getRightChild().getLeft());
                    getRightChild().setLeft(getRightChild().getRight());
                    getRightChild().setRight(null);
                    setRight(getRightChild().getLeft());
                }
                else { 
                    ((InternalNode) getMiddleChild()).setMiddleChild(
                            ((InternalNode) getRightChild()).getLeftChild());
                    ((InternalNode) getRightChild()).setLeftChild(
                            ((InternalNode) getRightChild()).getMiddleChild());
                    ((InternalNode) getRightChild()).setMiddleChild(
                            ((InternalNode) getRightChild()).getRightChild());
                    ((InternalNode) getRightChild()).setRightChild(null);
                    getMiddleChild().setLeft(updateValue(
                            ((InternalNode) getMiddleChild()).
                                getMiddleChild()));
                    setLeft(updateValue(getMiddleChild()));
                    setRight(updateValue(getRightChild()));
                    getRightChild().setRight(null);
                    getRightChild().setLeft(updateValue(((InternalNode) 
                            getRightChild()).getMiddleChild()));
                }
                return this;
            }
            else if (getRightChild() != null) {
                if (getLeftChild() instanceof LeafNode) {
                    getLeftChild().setRightSib(getMiddleChild().getRightSib());
                    if (getMiddleChild().getRightSib() != null) {
                        getMiddleChild().getRightSib().
                            setLeftSib(getLeftChild());
                    }
                    setMiddleChild(getRightChild());
                    setRightChild(null);
                    setLeft(getRight());
                    setRight(null);
                }
                else {
                    setLeft(updateValue(getRightChild()));
                    ((InternalNode) getLeftChild()).setRightChild(
                            ((InternalNode) getMiddleChild()).getLeftChild());
                    ((InternalNode) getLeftChild()).getRightChild().setRightSib(
                            ((InternalNode) getRightChild()).getLeftChild());
                    getLeftChild().setRight(updateValue(getMiddleChild()));
                    setMiddleChild(getRightChild());
                    getLeftChild().setRightSib(getMiddleChild());
                    getMiddleChild().setLeftSib(getLeftChild());
                    setRight(null);
                    setRightChild(null);
                }
                return this;
            }
            else {
                if (getLeftChild() instanceof InternalNode) {
                    getLeftChild().setRight(updateValue(getMiddleChild()));
                    ((InternalNode) getLeftChild()).setRightChild(
                            ((InternalNode) getMiddleChild()).getLeftChild());
                }
                getLeftChild().setRightSib(getMiddleChild().getRightSib());
                if (getMiddleChild().getRightSib() != null) {
                    getMiddleChild().setLeftSib(getLeftChild());
                }
                setLeft(null);
                setMiddleChild(null);
                return this;
            }
        }
        else {
            if (getMiddleChild().isFull()) {  
                if (getLeftChild() instanceof LeafNode) {     
                    setRight(getMiddleChild().getRight());
                    getRightChild().setLeft(
                            getMiddleChild().getRight());
                    getMiddleChild().setRight(null);
                }
                else {     
                    getRightChild().setLeft(updateValue(getRightChild()));
                    getRightChild().setRight(getMiddleChild().getRight());
                    ((InternalNode) getRightChild()).setMiddleChild(
                            ((InternalNode) getRightChild()).getLeftChild());
                    ((InternalNode) getRightChild()).setLeftChild(
                            ((InternalNode) getMiddleChild()).getRightChild());
                    ((InternalNode) getMiddleChild())
                        .setRightChild(null);
                    getMiddleChild().setRight(null);
                    setRight(updateValue(getRightChild()));
                } 
                return this;
            }
            else {
                if (getLeftChild() instanceof LeafNode) {
                    getMiddleChild().setRightSib(getRightChild().getRightSib());
                    if (getRightChild().getRightSib() != null) {
                        getRightChild().getRightSib().
                            setLeftSib(getMiddleChild());
                    }
                }
                else {
                    ((InternalNode) getMiddleChild()).setRightChild(
                            ((InternalNode) getRightChild()).getLeftChild());
                    getMiddleChild().setRightSib(getRightChild().getRightSib());
                    if (getRightChild().getRightSib() != null) {
                        getRightChild().getRightSib().
                            setLeftSib(getMiddleChild());
                    }
                    getMiddleChild().setRight(updateValue(
                            ((InternalNode) getMiddleChild()).getRightChild()));
                }
                setRightChild(null);
                setRight(null);
                return this;
            }
        }
    }
    
    /**
     * @param node is the inputnode
     * @param pair is the input kvpair
     * @return the updated node after deletion
     */
    public BaseNode delete(BaseNode node, KVPair pair) {    
        if (node instanceof LeafNode) {      
            return node.delete(node, pair);
        }
        else { 
            if (node.getLeft().compareTo(pair) > 0) {    
                BaseNode temp = delete(((InternalNode) node).getLeftChild()
                        , pair);
                if (temp.isEmpty()) {
                    BaseNode newNode = ((InternalNode) node).merge();
                    if (newNode.getLeftSib() == null &&
                            newNode.getRightSib() == null) {
                        if (((InternalNode) newNode).getMiddleChild() == null) {
                            return ((InternalNode) newNode).getLeftChild();
                        }
                    }
                    return newNode;
                }
                return node;
            }
            else if (node.getRight() == null ||
                    node.getRight().compareTo(pair) > 0) {         
                if (delete(((InternalNode) node).getMiddleChild()
                        , pair).isEmpty()) {
                    BaseNode newNode = ((InternalNode) node).merge();
                    if (newNode.getLeftSib() == null &&
                            newNode.getRightSib() == null) {
                        if (((InternalNode) newNode).getMiddleChild() == null) {
                            return ((InternalNode) newNode).getLeftChild();
                        }
                    }
                    return newNode;
                }
                node.setLeft(((InternalNode) node).updateValue(
                        ((InternalNode) node).getMiddleChild()));
                return node;
            }
            else {     
                if (delete(((InternalNode) node).getRightChild()
                        , pair).isEmpty()) {
                    BaseNode newNode = ((InternalNode) node).merge();
                    if (newNode.getRightSib() != null || 
                            newNode.getLeftSib() != null) {
                        return newNode;
                    }
                }
                node.setRight(updateValue(
                        ((InternalNode) node).getRightChild()));
                return node;
            }
        }
    }
}