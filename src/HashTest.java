import student.TestCase;

// -------------------------------------------------------------------------
/**
 *  Test the hash function 
 *
 *  @author shuaicheng zhang
 *  @version Aug 27, 2016
 */
public class HashTest extends TestCase {
    /**
     * Sets up the tests that follow.
     */
    public void setUp() {
        // Nothing Here
    }

    /**
     * Test the hash function
     * test insertion, expansion and removal
     */
    public void testInsertionRemoval() {
        
        Hash hasher = new Hash();
        assertEquals(10, hasher.getSize());
        
        Hash myHash = new Hash(10);
        MemoryPool pool = new MemoryPool(32);
        myHash.getPool(pool);
        String input = "Hellow World!";
       
        assertNull(myHash.remove(input));
        assertNotNull(myHash.insert(input, ""));
        assertNull(myHash.insert(input, ""));
        assertNotNull(myHash.remove(input));
        
        String num1 = "1";
        String num2 = "2";
        String num3 = "3";
        String num4 = "4";
        String num5 = "5";

        assertNotNull(myHash.insert(num1, ""));
        assertNotNull(myHash.remove(num1));
        assertNotNull(myHash.insert(num2, ""));
        assertNotNull(myHash.remove(num2));
        assertNotNull(myHash.insert(num3, ""));
        assertNotNull(myHash.remove(num3));
        assertNotNull(myHash.insert(num4, ""));
        assertNotNull(myHash.remove(num4));
        assertNotNull(myHash.insert(num5, ""));
        assertNotNull(myHash.remove(num5));

        
        myHash.insert(input, "");
        assertEquals(1, myHash.getElements());
        assertEquals(10, myHash.getSize());
        
        assertNotNull(myHash.insert(num1, ""));
        assertNotNull(myHash.insert(num2, ""));
        assertNotNull(myHash.insert(num3, ""));
        assertNotNull(myHash.insert(num4, ""));
        assertNotNull(myHash.insert(num5, ""));
        assertEquals(6, myHash.getElements());
        assertEquals(20, myHash.getSize());
        
        assertNotNull(myHash.remove(num3));
        assertNull(myHash.remove(num3));
        assertEquals(5, myHash.getElements());
        
        assertNotNull(myHash.insert("11", ""));
        assertNotNull(myHash.remove("11"));
        assertNotNull(myHash.insert("12", ""));
        assertNotNull(myHash.insert("13", ""));
        assertNotNull(myHash.insert("14", ""));
        assertNotNull(myHash.insert("15", ""));
        assertNotNull(myHash.insert("16", "")); 
        assertNotNull(myHash.insert("18", ""));
        assertEquals(11, myHash.getElements());
        assertEquals(40, myHash.getSize());
        
        myHash.dump();
        assertEquals(0, myHash.getElements());
    } 
    
    /**
     * test if the hash table can deal with tombstone correctly
     */
    public void testTomeStone() {

        Hash hasher = new Hash(10);
        MemoryPool pool = new MemoryPool(32);
        hasher.getPool(pool);
        
        //they can be hashed into the same slot
        assertNotNull(hasher.insert("5", ""));
        assertNotNull(hasher.insert("11", ""));
        assertNotNull(hasher.insert("112", ""));
        
        //to see if the hashtable skip tombstones to find duplicate objects
        assertNotNull(hasher.remove("5"));
        assertNotNull(hasher.remove("11"));
        assertNull(hasher.insert("112", ""));
        
        assertNotNull(hasher.insert("5", ""));
        assertNotNull(hasher.insert("11", ""));
        assertNull(hasher.insert("112", ""));
        assertNotNull(hasher.remove("11"));
        assertNotNull(hasher.insert("117", ""));
        assertNotNull(hasher.remove("112"));
        assertNotNull(hasher.insert("11", ""));
        
        assertNotNull(hasher.insert("221", ""));
        assertNotNull(hasher.insert("222", ""));
        assertNotNull(hasher.insert("223", ""));
        assertNotNull(hasher.insert("224", ""));
        assertNotNull(hasher.insert("225", ""));
        assertNotNull(hasher.insert("226", ""));
        assertNotNull(hasher.insert("227", ""));
        assertNotNull(hasher.insert("228", ""));
        assertNotNull(hasher.insert("229", ""));
        assertNotNull(hasher.insert("230", "")); 
        assertNotNull(hasher.insert("231", ""));
        assertNotNull(hasher.remove("223"));
        assertNull(hasher.insert("222", ""));
        assertNotNull(hasher.insert("233", ""));
        assertNotNull(hasher.remove("227"));
        assertNotNull(hasher.remove("228"));
        assertNull(hasher.insert("224", ""));
        assertNotNull(hasher.insert("234", ""));
        assertNotNull(hasher.insert("235", ""));
        
        assertNull(hasher.insert("226", ""));  
    }
}
