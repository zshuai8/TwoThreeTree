import student.TestCase;

/**
 * Test the KVPair class.
 * 
 * @author shuaicheng zhang
 * @version 9/16/2016
 */

public class LeafNodeTest extends TestCase {
    /**
     * Set up the tests that follow.
     */
    public void setUp() {
        // Nothing Here.
    }
    
    /**
     * Test methods in Leafnode class.
     */
    public void testLeaf() {

        Handle first = new Handle(1);
        Handle second = new Handle(2);
        KVPair myKV = new KVPair(first, second);
        KVPair lessKV = new KVPair(second, first);
        LeafNode leaf = new LeafNode();
        
        assertTrue(leaf.isEmpty());
        assertEquals("", leaf.toString());
        leaf.setLeft(lessKV);
        assertFalse(leaf.isEmpty());
        assertFalse(leaf.isFull());
        leaf.setRight(myKV);
        assertTrue(leaf.isFull());
        assertEquals("2 1 1 2", leaf.toString());
    }
     
}