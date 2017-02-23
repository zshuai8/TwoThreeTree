/**
 * 
 * @author shuaicheng zhang
 * @version 10/3/2016
 *
 */
public interface BaseNode {
    
    /**
     * 
     * @param inputPair is input kvpair
     */
    public void setLeft(KVPair inputPair);
    
    /**
     * 
     * @param inputPair is input kvpair
     */
    public void setRight(KVPair inputPair);
    
    /**
     * 
     * @return the left kvpair
     */
    public KVPair getLeft();
    
    /**
     * 
     * @return the right entry pointer
     */
    public KVPair getRight();
    
    /**
     * 
     * @return the left sibling
     */
    public BaseNode getLeftSib();
    
    /**
     * 
     * @return the right entry pointer
     */
    public BaseNode getRightSib();
    
    /**
     * @return return if the node is empty
     */
    public boolean isEmpty();
    
    /**
     * @return return if the node is empty
     */
    public boolean isFull();
    
    /**
     * 
     * @param node is the inputnode
     * @param pair is the input kvpair
     * @return the updated node
     */
    public BaseNode insert(BaseNode node, KVPair pair);
    
    /**
     * 
     * @param node is the input node
     * @return the updated node
     */
    public BaseNode add(BaseNode node);
    
    /**
     * @return the kvpair value
     */
    public String toString();
    
    /**
     * 
     * @param node the input node
     * set right sibling as the inputnode
     */
    public void setRightSib(BaseNode node);
    
    /**
     * 
     * @param node the input node
     * set the left sibling as the inputnode
     */
    public void setLeftSib(BaseNode node);
    
    /**
     * 
     * @param node is the inputNode
     * @param pair is the input pair
     * @return deleted updated node
     */
    public BaseNode delete(BaseNode node, KVPair pair);
}
