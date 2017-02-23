/**
 * Handle class definition
 *
 * @author shuaicheng zhang
 * @version 9/15/2016
 */

public class Handle
{
    /**
     * The position for the associated message in the memory pool
     */
    int thePos;
    private boolean tombstoneBoolean;


    // ----------------------------------------------------------
    /**
     * Create a new Handle object.
     *
     * @param p
     *            Value for position
     */
    public Handle(int p) {
        thePos = p;
        tombstoneBoolean = false;
    }


    // ----------------------------------------------------------
    /**
     * Overload compareTo
     *
     * @param it
     *            The handle being compared against
     * @return standard values of -1, 0, 1
     */
    public int compareTo(Handle it) {
        if (thePos < it.pos()) {
            return -1;
        } 
        else if (thePos == it.pos()) {
            return 0;
        } 
        else {
            return 1;
        }
    }


    // ----------------------------------------------------------
    /**
     * Getter for position
     *
     * @return The position
     */
    public int pos() {
        return thePos;
    }


    // ----------------------------------------------------------
    /**
     * Overload toString
     *
     * @return A print string
     */
    public String toString() {
        return String.valueOf(thePos);
    }
    
    /**
     * 
     * @param input is the boolean value we want the tombestone to be
     */
    public void setTombstone(boolean input) {
        
        tombstoneBoolean = input;
    }
    
    /**
     * 
     * @return if the handle is a tombstone
     */
    public boolean getTombstone() {
        
        return tombstoneBoolean;
    }
}
