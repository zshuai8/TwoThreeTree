import student.TestCase;

/**
 * Test the Handle class.
 * 
 * @author shuaicheng zhang
 * @version 9/16/2016
 */

public class HandleTest extends TestCase {

    /**
     * Set up the tests that follow.
     */
    public void setUp() {
        // Nothing Here.
    }
    
    /**
     * Test methods in Handle class. 
     */
    public void testH() {
        Handle myHandle = new Handle(1);
        Handle lessHandle = new Handle(2);
        Handle sameHandle = new Handle(1);
        Handle moreHandle = new Handle(0);
        assertEquals(myHandle.compareTo(lessHandle), -1);
        assertEquals(myHandle.compareTo(sameHandle), 0);
        assertEquals(myHandle.compareTo(moreHandle), 1);
        assertEquals(myHandle.pos(), 1);
        assertEquals(myHandle.toString(), "1");
    }
}