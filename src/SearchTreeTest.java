import student.TestCase;

/**
 * @author shuaicheng zhang
 * @version 10/3/2016
 */
public class SearchTreeTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization.
     */
    public void setUp() {
        // Nothing Here
    }

    /**
     * Get code coverage of the class declaration.
     */
    public void testSInit() {
        SearchTree tree = new SearchTree();
        assertNotNull(tree);
        String[] input = {"10", "32", "test1"};
        SearchTree.main(input);
        String[] input1 = {"10", "32", "testRemove"};
        SearchTree.main(input1);
    }
    
    /**
     * test input with less than three arguments
     */
    public void testNotEnough() {
        SearchTree tree = new SearchTree();
        assertNotNull(tree);
        String[] input = {"10", "32"};
        SearchTree.beginReadingInput(input);
        assertFuzzyEquals(systemOut().getHistory(), "Not enough input," 
                    + " please support with correct amount of inputs");
    }
    
    /**
     * test input with first argument not being an integer
     */
    public void testFirstArg() {
        SearchTree tree = new SearchTree();
        assertNotNull(tree);
        String[] input = {"hello", "32", "test1"};
        SearchTree.beginReadingInput(input);
        assertFuzzyEquals(systemOut().getHistory(), 
                "The first argument must be an integer.");
    }
    
    /**
     * test input with second argument not being an integer
     */
    public void testSecondArg() {
        SearchTree tree = new SearchTree();
        assertNotNull(tree);
        String[] input = {"10", "hello", "test1"};
        SearchTree.beginReadingInput(input);
        assertFuzzyEquals(systemOut().getHistory(), 
                "The second argument must be an integer.");
    }
    
}
