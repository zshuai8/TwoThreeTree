/**
 * a 2-3+ tree
 * @author shuaicheng zhang
 * @version 10/3/2016
 */
public class TTTree {
    
    private BaseNode root;
    //private boolean searched = false;
    
    /**
     * initialization
     */
    public TTTree() {
        
        root = null;
    }
    
    /**
     * 
     * @param pair in the input pair
     * @return true if insertion is successful
     */
    public boolean insert(KVPair pair) {
        
        if (root == null) {
            
            root = new LeafNode();
            root = root.insert(root, pair);
            return true;
        }
        
        if (findNode(root, pair) != null) {
            return false;
        }
        
        root = root.insert(root, pair);
        return true;
    }
    
    /**
     * 
     * @param pair is the input pair
     * @param currentRoot is the current node
     * @return the node if it exists or null
     */
    private BaseNode findNode(BaseNode currentRoot, KVPair pair) {
        
        if (currentRoot instanceof LeafNode) {
            
            if (currentRoot.getLeft().compareTo(pair) == 0) {
                
                return currentRoot;
            }
            else if (currentRoot.getRight() != null && 
                    currentRoot.getRight()
                    .compareTo(pair) == 0) {
                
                return currentRoot;
            }
            return null;
        }
        
        else {
            
            if (currentRoot.getLeft().compareTo(pair) > 0) {
                
                return findNode(((InternalNode) currentRoot)
                        .getLeftChild(), pair);
            }
            else if (currentRoot.getRight() == null || 
                    currentRoot.getRight().compareTo(pair) > 0) {
                
                return findNode(((InternalNode) currentRoot)
                        .getMiddleChild(), pair);
            }
            else {
                
                return findNode(((InternalNode) currentRoot)
                        .getRightChild(), pair);
            }
        }
    }
    
    
    /**
     * 
     * @param key is the input handle
     * @return the basenode contains the first key
     */
    public BaseNode firstPair(Handle key) {
        
        return findFirstPair(root, key);
    }
    
    /**
     * 
     * @param node is the input node
     * @param key is the input handle
     * @return the first node which contains the handle as the key
     */
    private BaseNode findFirstPair(BaseNode node, Handle key) {
        
        if (node == null) { 
            return null;
        }
        if (node instanceof LeafNode) {
            
            if (node.getLeft().compareTo(key) == 0
                    || (node.getRight() != null &&
                            node.getRight().compareTo(key) == 0)) {
                
                return node; 
            }
            
            if (node.getRightSib() != null && 
                    node.getRightSib().getLeft() != null &&
                    key.compareTo(node.getRightSib().getLeft().key()) == 0) {
                
                return node.getRightSib();
            }
            return null;
        }
        else {
            
            if (node.getLeft().key().compareTo(key) >= 0) {
                
                return findFirstPair(((InternalNode) node).getLeftChild(), key);
            }
            else if (node.getRight() == null || 
                    node.getRight().key().compareTo(key) >= 0) {
                
                return findFirstPair(((InternalNode) node)
                        .getMiddleChild(), key);
            }       
            else {
                
                return findFirstPair(((InternalNode) node)
                        .getRightChild(), key);
            }
        }
    }
    
    /**
     * 
     * @param key is the input key value
     * @return null or the first node which contains the key
     */
    public BaseNode searchKey(Handle key) {
        
        return findFirstPair(root, key);
    }
    
    /**
     * 
     * @param node is the input node
     * @param depth is the depth of the tree
     * @return a preorder string
     */
    public String preorderHelper(BaseNode node, int depth) {
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            
            sb.append(" ");
        }
        sb.append(node.getLeft());
        
        if (node.getRight() != null) {
            
            sb.append(" " + node.getRight());
        }
        sb.append("\n");
        if (!(node instanceof LeafNode)) {
            
            sb.append(preorderHelper(((InternalNode) node)
                    .getLeftChild() , depth + 2));
            sb.append(preorderHelper(((InternalNode) node)
                    .getMiddleChild() , depth + 2)); 
            if (((InternalNode) node).getRightChild() != null) {
                
                sb.append(preorderHelper(((InternalNode) node).
                        getRightChild() , depth + 2));
            }
        }
        return sb.toString();
    }
    
    /**
     * 
     * @param pair is the input KVpair
     * @return true if the pair in the tree is deleted
     */
    public boolean delete(KVPair pair) {
        
        if (root == null || findNode(root, pair) == null) {
            
            return false;
        }
        
        root = root.delete(root, pair);
        if (root.getLeft() == null) {
            root = null;
        }
        return true;
    }
    
    /**
     * 
     * @param key is the input key
     * @return true if the pair in the tree is deleted
     */
    public boolean remove(Handle key) {
        
        return findFirstPair(root, key) == null;
    }
    
    @Override
    /**
     * @return contents of the tree
     */
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        if (root != null) {
            
            sb.append(preorderHelper(root, 0));
        }
        return sb.toString();         
    }
}
