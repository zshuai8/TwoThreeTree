import student.TestCase;

/**
 * @author shuaicheng zhang
 * @version 2016.9.28
 */
public class InternalNodeTest extends TestCase {



    /**
     * Set up the tests that follow.
     */
    public void setUp() {
        // Nothing Here.
    }
    
    
    /**
     * Test methods in InternalNode class.
     */
    public void testInternalNode() {
    
        Handle first = new Handle(1);
        Handle second = new Handle(2);
        KVPair myKV = new KVPair(first, second);
        KVPair lessKV = new KVPair(second, first);
        InternalNode internal = new InternalNode();
        
        
        assertNull(internal.getLeft());
        assertNull(internal.getRight());
        assertNull(internal.getLeftChild());
        assertNull(internal.getRightChild());
        assertNull(internal.getMiddleChild());
        assertNull(internal.getLeftSib());
        assertNull(internal.getRightSib());
      
        assertTrue(internal.isEmpty());
        assertEquals("", internal.toString());
        internal.setLeft(lessKV);
        assertFalse(internal.isEmpty());
        assertFalse(internal.isFull());
        assertFalse(internal.isEmpty());
        assertFalse(internal.isFull());
        internal.setRight(myKV);
        assertTrue(internal.isFull());
        assertEquals("2 1 1 2", internal.toString());
        
        BaseNode testleft = null;
        BaseNode testright = null;
        BaseNode testmiddle = null;
    
        internal.setLeftChild(testleft);
        assertEquals(testleft, internal.getLeftChild());
        internal.setRightChild(testright);
        assertEquals(testright, internal.getRightChild());
        internal.setMiddleChild(testmiddle);
        assertEquals(testmiddle, internal.getMiddleChild());
      
        InternalNode testleftSib = new InternalNode();
        InternalNode testrightSib = new InternalNode();
         
        internal.setLeftSib(testleftSib);
        assertEquals(testleftSib, internal.getLeftSib());
        internal.setRightSib(testrightSib);
        assertEquals(testrightSib, internal.getRightSib());
    }
}