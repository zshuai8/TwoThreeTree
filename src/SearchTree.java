/**
 * {Project Description Here}
 */

/**
 * The class containing the main method.
 *
 * @author shuaicheng zhang
 * @version 2016/9/10
 */

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class SearchTree {
    private static int hashSize;
    private static int blockSize = 0;
    private static String fileName;
    /**
     * @param args
     *            Command line parameters
     */
    public static void main(String[] args) {
        
        beginReadingInput(args);
        new Parser(hashSize, blockSize, fileName);
    }
    
    /**
     * 
     * @param input
     *            is the command lines parsing command lines
     * @return true if parser successfully parsed the input
     * return null else.
     */
    public static boolean beginReadingInput(String[] input) {
 
        if (input.length < 3) {

            System.out.println("Not enough input," 
                    + " please support with correct amount of inputs");
            return false;
        }

        try {

            hashSize = Integer.parseInt(input[0]);
        }
        catch (NumberFormatException nfe) {

            System.out.println(
                    "The first argument must be an integer.");
            return false;
        }

        try {

            blockSize = Integer.parseInt(input[1]);
        }
        catch (NumberFormatException nfe) {

            System.out.println(
                    "The second argument must be an integer.");
            return false;
        }

        fileName = input[2];
        return true;
    }
}
