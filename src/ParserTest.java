import student.TestCase;

/**
 * @author shuaicheng zhang
 * @version 2016/8/28
 */
public class ParserTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing Here
    }

    /**
     * check basic functionality of parser class
     */
    public void testParser() {
        
        Parser parser = new Parser(10, 32, "test1");   
        assertTrue(parser.beginParsingByLine("test2.txt"));
        new Parser(10, 32, "P2_Input1_Sample (1).txt");
        Parser cmdCheckParser = new Parser(10, 10, "P2_Input1_Sample.txt"); 
        assertFalse(cmdCheckParser.beginParsingByLine("cmdCheck.txt"));
        Parser parser1 = new Parser(10, 32, "P2_Input1_Sample.txt");  
        assertTrue(parser1.beginParsingByLine("P2_Input1_Sample.txt"));
    } 
}