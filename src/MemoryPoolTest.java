import student.TestCase;

/**
 * 
 * @author shuaicheng zhang
 * @version 9/10/2016
 *
 */
public class MemoryPoolTest extends TestCase {
    
    private MemoryPool pool;
    
    /**
     * Set up the tests that follow.
     * creates a new memory pool with initial size 32
     */
    public void setUp() {
        
        pool = new MemoryPool(32);
    }
    
    /**
     * test simple insert of memory pool
     */
    public void testInsert() {
        
        String input = "123";
        byte[] space = input.getBytes();
        int size = space.length;
        Handle spaceHandle =  pool.insert(space, size);
        
        assertEquals("123", new String(pool.get(spaceHandle)));
    }
    
    /**
     * test simple remove of memory pool
     */
    public void testRemove() {
        
        String input = "12345678";
        byte[] space = input.getBytes();
        int size = space.length;
        Handle spaceHandle =  pool.insert(space, size);
        pool.remove(spaceHandle);
        assertEquals("", new String(pool.get(spaceHandle)));
    }


    /**
     * test insert large size byte array
     */

    public void testInsertLarge() {
        
        long input = 0xfffffff;
        byte[] space = new byte[(int)input];
        int size = space.length;
        assertNull(pool.insert(space, size));
        
    }
}