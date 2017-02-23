import student.TestCase;

/**
 * @author shuaicheng zhang
 * @version 2016/8/28
 */
public class TTTreeTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing Here
    }
    
    /**
     * test delete nodes from a level 4 tree
     */
    public void testLevel4TreeDelete() {
        Handle artist1 = new Handle(0);
        Handle artist2 = new Handle(103);
        Handle artist3 = new Handle(177);
        Handle artist4 = new Handle(39);
        
        Handle song1 = new Handle(13);
        Handle song2 = new Handle(76);
        Handle song3 = new Handle(124);
        Handle song4 = new Handle(150);
        Handle song5 = new Handle(58);
        
        TTTree tree = new TTTree();
        tree.insert(new KVPair(artist1, song1));
        tree.insert(new KVPair(song1, artist1));
        tree.insert(new KVPair(artist1, song2));
        tree.insert(new KVPair(song2, artist1));
        tree.insert(new KVPair(artist1, song3));
        tree.insert(new KVPair(song3, artist1));
        tree.insert(new KVPair(artist1, song4));
        tree.insert(new KVPair(song4, artist1));
        tree.insert(new KVPair(artist4, song5));
        tree.insert(new KVPair(song5, artist4));
        tree.insert(new KVPair(artist2, song1));
        tree.insert(new KVPair(song1, artist2));
        tree.insert(new KVPair(artist2, song2));
        tree.insert(new KVPair(song2, artist2));
        tree.insert(new KVPair(artist2, song3));
        tree.insert(new KVPair(song3, artist2));
        tree.insert(new KVPair(artist3, song1));
        assertTrue(tree.insert(new KVPair(song1, artist3)));
        tree.delete(new KVPair(song1, artist1));
        tree.delete(new KVPair(artist1, song2));
        tree.delete(new KVPair(artist1, song3));
        //tree.delete(new KVPair(artist1, song4));
        
        BaseNode node = tree.firstPair(artist1);
        assertEquals(node.getRightSib().getLeft().compareTo(
                new KVPair(artist1, song4)), 0);
        node = node.getRightSib();
        assertEquals(node.getRightSib().getLeft().compareTo(
                new KVPair(song1, artist2)), 1);
        
    }
    
    
    /**
     * test delete on null root
     */
    public void testNullRoot() {
        TTTree tree = new TTTree();
        Handle artist = new Handle(0);
        Handle song = new Handle(13);
        assertEquals(tree.delete(new KVPair(artist, song)), false);
        assertTrue(tree.remove(artist));
        assertTrue(tree.insert(new KVPair(artist, song)));
        assertTrue(tree.remove(song));
        
        
    }
    
    /**
     * test insert duplicate KVPairs
     */
    public void testInsert() {
         
        Handle first = new Handle(1);
        Handle second = new Handle(2);
        KVPair myKV = new KVPair(first, second);
        KVPair lessKV = new KVPair(second, first);
        TTTree tree = new TTTree();
        
        assertTrue(tree.insert(myKV));
        assertFalse(tree.insert(myKV));
        assertTrue(tree.insert(lessKV));
        assertFalse(tree.insert(lessKV));
        assertEquals("1 2 2 1\n", tree.toString());
    }
    
    /**
     * test search for the first pair in the tree
     */
    public void testSearchFirstPair() {
        
        Handle first = new Handle(1);
        TTTree tree = new TTTree();
        assertNull(tree.searchKey(first));
    }
    
    /**
     * test delete left when child node is a leaf node
     */
    public void testTreeDeleteLeft() {
        
        Handle first = new Handle(1);
        Handle second = new Handle(2);
        KVPair myKV = new KVPair(first, second);
        KVPair lessKV = new KVPair(second, first);
        TTTree internal = new TTTree();
        internal.insert(myKV);
        internal.insert(lessKV);
        
        Handle third = new Handle(3);
        Handle forth = new Handle(4);
        KVPair newKV = new KVPair(third, forth);
        KVPair newKV2 = new KVPair(forth, third);
        internal.insert(newKV);
        internal.insert(newKV2);
        internal.delete(myKV);
        assertTrue(internal.delete(lessKV));
    }
    
    /**
     * delete left when child node is an internal node
     */
    public void testDeleteInternalLeft() {
        
        Handle first = new Handle(1);
        Handle second = new Handle(2);
        KVPair myKV = new KVPair(first, second);
        KVPair lessKV = new KVPair(second, first);
        TTTree internal = new TTTree();
        internal.insert(myKV);
        internal.insert(lessKV);
        
        Handle third = new Handle(3);
        Handle forth = new Handle(4);
        KVPair newKV = new KVPair(third, forth);
        KVPair newKV2 = new KVPair(forth, third);
        internal.insert(newKV);
        internal.insert(newKV2);
        
        Handle fifth = new Handle(5);
        Handle sixth = new Handle(6);
        KVPair newKV3 = new KVPair(fifth, sixth);
        KVPair newKV4 = new KVPair(sixth, fifth);
        assertTrue(internal.insert(newKV3));
        assertTrue(internal.insert(newKV4));
        internal.delete(myKV);
        internal.delete(lessKV);
        internal.delete(newKV);
        assertTrue(internal.delete(newKV2));
        assertTrue(internal.delete(newKV3));
        assertTrue(internal.delete(newKV4));
    }
    
    /**
     * test delete right when child node is a leaf node
     */
    public void testDeleteLeafRight() {
        
        Handle first = new Handle(1);
        Handle second = new Handle(2);
        KVPair myKV = new KVPair(first, second);
        KVPair lessKV = new KVPair(second, first);
        TTTree internal = new TTTree();
        internal.insert(myKV);
        internal.insert(lessKV);
        
        Handle third = new Handle(3);
        Handle forth = new Handle(4);
        KVPair newKV = new KVPair(third, forth);
        KVPair newKV2 = new KVPair(forth, third);
        assertTrue(internal.insert(newKV));
        assertTrue(internal.insert(newKV2));
        
        Handle fifth = new Handle(5);
        Handle sixth = new Handle(6);
        KVPair newKV3 = new KVPair(fifth, sixth);
        KVPair newKV4 = new KVPair(sixth, fifth);
        internal.insert(newKV3);
        internal.insert(newKV4);
        assertTrue(internal.delete(newKV4));
        assertTrue(internal.delete(newKV3));
    }
    
    /**
     * test delete right when child node is an internal node
     */
    public void testDeleteInternalRight() {
        
        Handle first = new Handle(1);
        Handle second = new Handle(2);
        KVPair myKV = new KVPair(first, second);
        KVPair lessKV = new KVPair(second, first);
        TTTree internal = new TTTree();
        internal.insert(myKV);
        internal.insert(lessKV);
        
        Handle third = new Handle(3);
        Handle forth = new Handle(4);
        KVPair newKV = new KVPair(third, forth);
        KVPair newKV2 = new KVPair(forth, third);
        internal.insert(newKV);
        internal.insert(newKV2);
        
        Handle fifth = new Handle(5);
        Handle sixth = new Handle(6); 
        KVPair newKV3 = new KVPair(fifth, sixth);
        KVPair newKV4 = new KVPair(sixth, fifth);
        internal.insert(newKV3);
        internal.insert(newKV4);
        
        Handle seventh = new Handle(7);
        Handle eighth = new Handle(8);
        KVPair newKV5 = new KVPair(seventh, eighth);
        KVPair newKV6 = new KVPair(eighth, seventh);
        internal.insert(newKV5);
        internal.insert(newKV6);
        internal.delete(newKV5);
        internal.delete(newKV3);
        internal.delete(newKV);
        assertTrue(internal.delete(newKV2));
        assertTrue(internal.delete(lessKV));
        assertTrue(internal.delete(myKV));
        assertTrue(internal.delete(newKV6));
    }
    
    
    /**
     * test remove method
     */
    public void testRemove() {
        Handle first = new Handle(1);
        Handle second = new Handle(2);
        KVPair myKV = new KVPair(first, second);
        KVPair lessKV = new KVPair(second, first);
        TTTree tree = new TTTree();
        
        assertTrue(tree.remove(first));
        assertTrue(tree.remove(second));
        tree.insert(lessKV);
        tree.insert(myKV);
        assertFalse(tree.remove(first));
        assertFalse(tree.remove(second));
    }
    
    /**
     * test add method of LeafNode class
     */
    public void testInsert2() {
        Handle first = new Handle(1);
        Handle second = new Handle(2);
        Handle third = new Handle(4);
        KVPair myKV = new KVPair(first, second);
        KVPair lessKV = new KVPair(first, third);
        KVPair pair1 = new KVPair(third, first);
        KVPair pair2 = new KVPair(third, second);
        KVPair pair3 = new KVPair(second, first);
        TTTree tree = new TTTree();

        tree.insert(lessKV);
        tree.insert(myKV);
        tree.insert(pair1);
        tree.insert(pair2);
        assertTrue(tree.insert(pair3));
        assertEquals("1 4 4 1\n  1 2"
                + "\n  1 4 2 1\n  4 1 4 2\n", tree.toString());
        assertTrue(tree.insert(new KVPair(first, new Handle(3))));
        assertTrue(tree.insert(new KVPair(first, first)));
        assertTrue(tree.insert(new KVPair(first, new Handle(5))));
        assertEquals("1 4\n  1 2\n    1 2\n    1 2 1 3\n  1 5 4 1"
                + "\n    1 4\n    1 5 2 1\n    4 1 4 2\n",
                tree.toString());
    }
    
    /**
     * test add and delete methods of LeafNode class
     */
    public void testLeafNodeAddAndDelete() {
         
        Handle first = new Handle(1);
        Handle second = new Handle(2);
        Handle third = new Handle(3);
        Handle forth = new Handle(4);
        TTTree tree =  new TTTree();
        
        assertNull(tree.firstPair(new Handle(5)));
        assertNull(tree.firstPair(new Handle(3)));
        
        KVPair firstp = new KVPair(first, second);
        KVPair secondp = new KVPair(second, first);
        KVPair thirdp = new KVPair(first, forth);
        KVPair forthp = new KVPair(forth, first);
        KVPair fifth = new KVPair(first, third);
        
        assertTrue(tree.insert(thirdp));
        assertTrue(tree.insert(forthp));
        assertTrue(tree.insert(firstp));
        assertTrue(tree.insert(secondp));
        assertTrue(tree.insert(fifth));
        assertEquals(tree.toString(), "1 4 2 1\n  1 3 1 4"
                + "\n  1 4\n  2 1 4 1\n");
        assertNull(tree.firstPair(new Handle(0)));
        assertNull(tree.firstPair(new Handle(5)));
        assertNull(tree.firstPair(new Handle(3)));
        assertNotNull(tree.firstPair(forth));
        assertNotNull(tree.firstPair(second));
        assertNotNull(tree.firstPair(first));
        
        tree.insert(new KVPair(first, new Handle(5)));
        assertEquals(tree.toString(), "1 4 2 1\n  1 3 1 4"
                + "\n  1 4 1 5\n  2 1 4 1\n");
        tree.delete(forthp);
        tree.delete(secondp);
        assertEquals(tree.toString(), "1 4 1 5\n  1 3 1 4"
                + "\n  1 4\n  1 5\n");
        tree.insert(new KVPair(first, new Handle(5)));
        tree.delete(new KVPair(new Handle(5), first));
    }
    
    /**
     * test merge method of InternalNode class
     */
    public void testMerge() {
        Handle first = new Handle(1);
        Handle second = new Handle(2);
        Handle third = new Handle(3);
        Handle fourth = new Handle(4);
        Handle hundred = new Handle(100);
        Handle onetwenty = new Handle(120);
        KVPair myKV = new KVPair(first, second);
        KVPair lessKV = new KVPair(first, fourth);
        KVPair pair1 = new KVPair(fourth, first);
        KVPair pair2 = new KVPair(fourth, second);
        KVPair pair3 = new KVPair(second, first);
        KVPair pair100 = new KVPair(hundred, onetwenty);
        KVPair pair100r = new KVPair(onetwenty, hundred);
        TTTree tree = new TTTree();

        tree.insert(lessKV);
        tree.insert(myKV);
        tree.insert(pair1);
        tree.insert(pair2);
        assertTrue(tree.insert(pair3));
        assertEquals(tree.toString(), "1 4 4 1\n  1 2"
                + "\n  1 4 2 1\n  4 1 4 2\n");
        assertTrue(tree.insert(new KVPair(first, new Handle(3))));
        assertTrue(tree.insert(new KVPair(first, first)));
        assertTrue(tree.insert(new KVPair(first, new Handle(5))));
        assertEquals("1 4\n  1 2\n    1 2\n    1 2 1 3\n  1 5 4 1"
                + "\n    1 4\n    1 5 2 1\n    4 1 4 2\n",
                tree.toString());
        tree.insert(pair100);
        tree.insert(pair100r);
        assertEquals(tree.toString(), "1 4 4 1\n  1 2\n    1 2\n"
                + "    1 2 1 3\n  1 5\n    1 4\n"
                + "    1 5 2 1\n  4 2 100 120\n"
                + "    4 1\n    4 2\n    100 120 120 100\n");
        tree.insert(new KVPair(third, first));
        tree.delete(pair100);
        tree.delete(pair100r);
        tree.delete(pair2);
        assertEquals(tree.toString(), "1 4 2 1\n  1 2\n    1 2\n"
                + "    1 2 1 3\n  1 5\n    1 4\n"
                + "    1 5\n  4 1 2 1\n"
                + "    2 1 3 1\n    4 1\n");
        
    } 
    
}
    


