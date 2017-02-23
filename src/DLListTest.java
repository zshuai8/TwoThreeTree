import student.TestCase;

/**
 * @author shuaicheng zhang
 * @version 9/2/2016
 *
 */
public class DLListTest extends TestCase {
    /**
     * the list we will use
     */
    MemoryPool manager;

    /**
     * run before every test case
     */
    @Override
    public void setUp() {
        
        manager = new MemoryPool(20);
    }
    
    /**
     * test insert function
     * insert the memory pool to see if insertion is successful
     * check expansion function and best fit function
     */
    public void testInsert() {
        
        byte[] newbyte = "123".getBytes(); 
        byte[] newbyte1 = "234".getBytes();
        byte[] newbyte2 = "345".getBytes();
        byte[] newbyte3 = "456".getBytes();
        byte[] newbyte4 = "567".getBytes();
        
       
        Handle newhandle = manager.insert(newbyte, 3);
        Handle newhandle1 = manager.insert(newbyte1, 3);
        Handle newhandle2 = manager.insert(newbyte2, 3);
        Handle newhandle3 = manager.insert(newbyte3, 3);
        assertEquals(manager.size(), 20);
        assertFalse(manager.poolDoubled());
        Handle newhandle4 = manager.insert(newbyte4, 3);
        assertTrue(manager.poolDoubled());
        assertEquals(manager.size(), 40);
        
        assertEquals(new String(manager.get(newhandle)), "123");
        assertEquals(new String(manager.get(newhandle1)), "234");
        assertEquals(new String(manager.get(newhandle2)), "345");
        assertEquals(new String(manager.get(newhandle3)), "456");
        
        assertEquals(new String(manager.get(newhandle4)), "567");
    }


    /**
     * test remove function
     * to test if memory pool correctly removes handle
     */
    public void testRemove() {
        
        byte[] newbyte = "123".getBytes();
        Handle newhandle = manager.insert(newbyte, 3);
        assertEquals(manager.getList().toString(), "(5,15)");
        assertEquals(manager.getList().size(), 1);
        
        assertEquals(new String(manager.get(newhandle)), "123");
        manager.remove(newhandle);
        assertEquals(new String(manager.get(newhandle)), "");
        assertEquals(manager.getList().toString(), "(0,20)");
        assertEquals(manager.getList().size(), 1);
        
        byte[] newbyte1 = "234".getBytes();
        byte[] newbyte2 = "345".getBytes();
        byte[] newbyte3 = "456".getBytes();
        byte[] newbyte4 = "567".getBytes();
        byte[] newbyte5 = "678".getBytes();
        
       
        Handle reinserted = manager.insert(newbyte, 3);
        Handle newhandle1 = manager.insert(newbyte1, 3);
        Handle newhandle2 = manager.insert(newbyte2, 3);
        Handle newhandle3 = manager.insert(newbyte3, 3);
        assertEquals(manager.size(), 20);
        assertFalse(manager.poolDoubled());
        assertEquals(manager.getList().toString(), "(20,0)");
        assertEquals(manager.getList().size(), 1);
        
        
        assertEquals(new String(manager.get(reinserted)), "123");
        assertEquals(new String(manager.get(newhandle1)), "234");
        assertEquals(new String(manager.get(newhandle2)), "345");
        assertEquals(new String(manager.get(newhandle3)), "456");       
        
        manager.remove(reinserted);
        assertEquals(manager.getList().toString(), "(0,5)");
        assertEquals(manager.getList().size(), 1);
        
        manager.remove(newhandle3);
        assertEquals(manager.getList().toString(), "(0,5)"
                + " -> (15,5)");
        assertEquals(manager.getList().size(), 2);
        
        Handle newhandle4 = manager.insert(newbyte4, 3);
        assertEquals(new String(manager.get(newhandle4)), "567");
        assertEquals(manager.getList().toString(), "(15,5)");
        assertEquals(manager.getList().size(), 1);
        
        Handle newhandle5 = manager.insert(newbyte5, 3);
        assertEquals(new String(manager.get(newhandle5)), "678");
        assertEquals(manager.getList().toString(), "(20,0)");
        assertEquals(manager.getList().size(), 1);
        
        
        manager.remove(newhandle4);
        manager.remove(newhandle2);
        assertEquals(manager.getList().toString(), "(0,5) -> (10,5)");
        assertEquals(manager.getList().size(), 2);
        manager.remove(newhandle1);
        assertEquals(manager.getList().toString(), "(0,15)");
        assertEquals(manager.getList().size(), 1);
        
        byte[] word1 = "hello".getBytes();
        Handle wordhandle1 = manager.insert(word1, 5);
        assertEquals(new String(manager.get(wordhandle1)), "hello");
        assertEquals(manager.getList().size(), 1);
        assertEquals(manager.getList().toString(), "(7,8)");
        
        byte[] word2 = "hello!".getBytes();
        Handle wordhandle2 = manager.insert(word2, 6);
        assertEquals(new String(manager.get(wordhandle2)), "hello!");
        assertEquals(manager.getList().size(), 1);
        assertEquals(manager.getList().toString(), "(20,0)");
        
        byte[] word3 = "hello!!!".getBytes();
        Handle wordhandle3 = manager.insert(word3, 8);
        assertEquals(new String(manager.get(wordhandle3)), "hello!!!");
        assertEquals(manager.getList().size(), 1);
        assertEquals(manager.getList().toString(), "(30,10)");
    }
    
    /**
     * test more cases of remove method to see some extreme cases
     * case empty list and full list
     */
    public void testRemoveMore() {
        
        Handle fullPool = manager.insert("123456789012345678".getBytes(), 18);
        assertEquals("123456789012345678", new String(manager.get(fullPool)));
        assertEquals(manager.getList().size(), 1);
        assertEquals(manager.getList().toString(), "(20,0)");
        manager.remove(fullPool);
        assertEquals(manager.getList().toString(), "(0,20)");
        assertFalse(manager.getList().remove(100));
        
        Handle newHandle = manager.insert("12345".getBytes(), 5);
        Handle newHandle1 = manager.insert("23456".getBytes(), 5);
        Handle newHandle2 = manager.insert("3456".getBytes(), 4);
        assertEquals(new String(manager.get(newHandle1)), "23456"); 
        assertEquals(new String(manager.get(newHandle2)), "3456");
        assertEquals(new String(manager.get(newHandle)), "12345"); 
        assertEquals(manager.getList().toString(), "(20,0)");
        manager.remove(newHandle1);
        assertEquals(manager.getList().toString(), "(7,7)");
        Handle newHandle3 = manager.insert("45678".getBytes(), 5);
        assertEquals(new String(manager.get(newHandle3)), "45678");
        assertEquals(manager.getList().toString(), "(20,0)");
        
        Handle expandedPool = manager.insert("123456789012345678".getBytes()
                , 18);
        assertEquals(manager.getList().toString(), "(40,0)");
        assertEquals(new String(manager.get(expandedPool)), 
                "123456789012345678");
        manager.remove(expandedPool);
        assertEquals(manager.getList().toString(), "(20,20)");
    }
}
