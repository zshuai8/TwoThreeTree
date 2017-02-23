import java.io.File;
import java.util.Scanner;


/**
 * A parser class which parses input arguments
 * 
 * @author shuaicheng zhang
 * @version 9/4/2016
 *
 */
public class Parser {

    private Hash artistHash;
    private Hash songHash;
    private MemoryPool pool;
    private TTTree tree;

    /**
     * 
     * @param inputHashSize
     *            is the input hashtable size
     * @param inputBlockSize
     *            is the input memory pool size
     * @param inputFileName
     *            is the input file name
     */
    public Parser(int inputHashSize, int inputBlockSize, String inputFileName) {

        artistHash = new Hash(inputHashSize);
        songHash = new Hash(inputHashSize);
        pool = new MemoryPool(inputBlockSize);
        artistHash.getPool(pool);
        songHash.getPool(pool);
        tree = new TTTree();

        String filename = inputFileName;
        beginParsingByLine(filename);
    }


    /**
     * 
     * @param filename
     *            is the input file parsing the input file
     * @return true if parsing is successful, else return false
     */
    public boolean beginParsingByLine(String filename) {

        try {

            Scanner sc = new Scanner(new File(filename));
            Scanner scancmd;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                scancmd = new Scanner(line);
                String cmd = scancmd.next();
                String type;
                
                if (cmd.equals("insert")) {
                    scancmd.useDelimiter("<SEP>");
                    String artist = scancmd.next().replaceFirst(" ", "");
                    String song = scancmd.next();
                    insert(song, artist);
                }
                else if (cmd.equals("remove")) {
                    type = scancmd.next();
                    String token = scancmd.nextLine().replaceFirst(" ", "");
                    remove(type, token);
                }
                else if (cmd.equals("print")) {
                    type = scancmd.next();
                    print(type);
                }
                else if (cmd.equals("delete")) {
                    scancmd.useDelimiter("<SEP>");
                    String deleteArtist = scancmd.next().replaceFirst(" ",
                            "");
                    String deleteSong = scancmd.next();
                    treeDelete(deleteArtist, deleteSong);
                }
                else if (cmd.equals("list")) {
                    type = scancmd.next();
                    String name = scancmd.nextLine().replaceFirst(" ", "");
                    list(type, name);
                }
                else { 
                    System.out.println("Unrecognized input");
                }
                
            }

            sc.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * we print out results accordinly to the input song and artist
     * 
     * @param inputSong
     *            is the inputsong
     * @param inputArtist
     *            is the input artist
     */
    public void insert(String inputSong, String inputArtist) {

        String song = inputSong;
        String artist = inputArtist;
        int songIndex = songHash.contain(song);
        int artistIndex = artistHash.contain(artist);
        
        if (artistIndex == -1) {
            artistHash.insert(artist, "Artist Hash");
            System.out.println(
                    "|" + artist + "| is added to the artist database.");
        }
        else {

            System.out.println("|" + artist + "| duplicates a record "
                    + "already in the artist database.");
        }

        
        if (songIndex == -1) {
            songHash.insert(song, "Song Hash");
            System.out.println("|" + song + "| is added to the song database.");
        }
        else {

            System.out.println("|" + song + "| duplicates a record "
                    + "already in the song database.");
        }
        
        int indexArtist = artistHash.contain(inputArtist);
        Handle artistHandle = artistHash.retrieveHandle(indexArtist);

        int indexSong = songHash.contain(inputSong);
        Handle songHandle = songHash.retrieveHandle(indexSong);
        
        KVPair newPair = new KVPair(artistHandle, songHandle);
        KVPair newPair2 = new KVPair(songHandle, artistHandle);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb1.append("The KVPair (|" + artist + "|" + ",|" + song + "|)," + "("
                + artistHandle + "," + songHandle + ")");
        sb2.append("The KVPair (|" + song + "|" + ",|" + artist + "|)," + "("
                + songHandle + "," + artistHandle + ")");

        if (tree.insert(newPair)) {
            sb1.append(" is added to the tree.\n");
        }
        else {

            sb1.append(" duplicates a record already in the tree.\n");
        }
        if (tree.insert(newPair2)) {
            sb2.append(" is added to the tree.");
        }
        else {
            sb2.append(" duplicates a record already in the tree.");
        }
        System.out.println(sb1.toString() + sb2.toString());
    }

    /**
     * we remove an element of a hashtable accordingly to the input and command
     * 
     * @param command
     *            is the input command
     * @param input
     *            is the input name
     */
    public void remove(String command, String input) {
        String type = command;
        String removed = input;

        if (type.equals("artist")) {
            Handle artistHandle = artistHash.remove(input);

            if (artistHandle != null) {
    
                BaseNode node = tree.firstPair(artistHandle);
                StringBuilder str = new StringBuilder();
                while (node != null) {
                        
                    Handle leftKey = node.getLeft().key();
                    Handle leftValue = node.getLeft().value();
                    Handle rightKey = null;
                    Handle rightValue = null;
                    if (node.getRight() != null) {
                        rightKey = node.getRight().key();
                        rightValue = node.getRight().value();
                    }

                    String song;
                    if (leftKey.compareTo(artistHandle) == 0) {
                        
                        tree.delete(new KVPair(leftKey, leftValue));
                        tree.delete(new KVPair(leftValue, leftKey));
                        song = new String(pool.get(leftValue));
                            
                        str.append("The KVPair (|" + input + "|" + ",|"
                                + song + "|)" + 
                                    " is deleted from the tree.\n");
                        str.append("The KVPair (|" + song + "|" + ",|"
                                + input + "|)" 
                                    + " is deleted from the tree.\n");
                            
                        if (tree.firstPair(artistHandle) == null) {
                            str.append("|" + removed + "| is deleted from "
                                    + "the artist database.\n");
                        }
                        if (tree.firstPair(leftValue) == null) {
                            str.append("|" + new String(pool.get(leftValue))
                                    + "| is deleted "
                                    + "from the song database.\n");
                            songHash.
                                remove(new String(pool.get(leftValue)));
                        }
                    }
                    if (rightKey != null 
                            && rightKey.compareTo(artistHandle) == 0) {
                        tree.delete(new KVPair(rightKey, rightValue));
                        tree.delete(new KVPair(rightValue, rightKey));
                        song = new String(pool.get(rightValue));
                        
                        str.append("The KVPair (|" + input + "|" + ",|"
                                + song + "|)" 
                                    + " is deleted from the tree.\n");
                        str.append("The KVPair (|" + song + "|" + ",|"
                                + input + "|)" 
                                    + " is deleted from the tree.\n");
                            
                        if (tree.firstPair(artistHandle) == null) {
                            str.append("|" + removed + "| is deleted from "
                                    + "the artist database.\n");
                        }
                        if (tree.firstPair(rightValue) == null) {
                            str.append("|" 
                                    + new String(pool.get(rightValue))
                                    + "| is deleted " 
                                    + "from the song database.\n");
                            songHash.
                                remove(new String(pool.get(rightValue)));
                        }

                    }
                    node = tree.firstPair(artistHandle);
                }
                System.out.print(str.toString());
            }
            else {
    
                System.out.println("|" + removed + "| does not exist "
                        + "in the artist database.");
            }
        }
        else if (type.equals("song")) {
            
            Handle songHandle = songHash.remove(input);
            if (songHandle != null) {
                StringBuilder str = new StringBuilder();
                BaseNode node = tree.firstPair(songHandle);
                while (node != null) {
                        
                    Handle leftKey = node.getLeft().key();
                    Handle leftValue = node.getLeft().value();
                    Handle rightKey = null;
                    Handle rightValue = null;
                    if (node.getRight() != null) {
                        rightKey = node.getRight().key();
                        rightValue = node.getRight().value();
                    }
    
                    String artist;
                    if (leftKey.compareTo(songHandle) == 0) {
    
                        tree.delete(new KVPair(leftKey, leftValue));
                        tree.delete(new KVPair(leftValue, leftKey));
                        artist = new String(pool.get(leftValue));
                        str.append("The KVPair (|" + input + "|" + ",|"
                                + artist + "|)" 
                                    + " is deleted from the tree.\n");
                        str.append("The KVPair (|" + artist + "|" + ",|"
                                + input + "|)" 
                                    + " is deleted from the tree.\n");
                            
                        if (tree.firstPair(songHandle) == null) {
                            str.append("|" + removed + "| is deleted "
                                    + "from the song database.\n");
                        }
                        if (tree.firstPair(leftValue) == null) {
                            str.append("|" + new String(pool.get(leftValue))
                                    + "| is deleted "
                                    + "from the artist database.\n");
                            artistHash.
                                remove(new String(pool.get(leftValue)));
                        }
                    }
                    if (rightKey != null
                            && rightKey.compareTo(songHandle) == 0) {
                        tree.delete(new KVPair(rightKey, rightValue));
                        tree.delete(new KVPair(rightValue, rightKey));
                        artist = new String(pool.get(rightValue));
                        str.append("The KVPair (|" + input + "|" + ",|"
                                + artist + "|)" + " is deleted "
                                       + "from the tree.\n");
                        str.append("The KVPair (|" + artist + "|" + ",|"
                                + input + "|)" + " is deleted "
                                        + "from the tree.\n");
                            
                        if (tree.firstPair(songHandle) == null) {
                            str.append("|" + removed + "| is deleted "
                                    + "from the song database.\n");
                        }
                        if (tree.firstPair(rightValue) == null) {
                            str.append("|" + new String(
                                    pool.get(rightValue)) + 
                                        "| is deleted "
                                    + "from the artist database.\n");
                            artistHash.remove(new String(
                                    pool.get(rightValue)));
                        }
                    }
                    node = tree.firstPair(songHandle);
                }
    //                System.out.println("|" + removed + "| is deleted "
    //                        + "from the song database.");
                System.out.print(str.toString());
            }
            else {
   
                System.out.println("|" + removed + "| does not exist "
                        + "in the song database.");
    
            }
        }
        else {
            System.out.println("Error bad remove type " + removed);
        }
        
    }

    /**
     * 
     * @param inputArtist
     *            is the input artist name
     * @param inputSong
     *            is the input song name
     */
    public void treeDelete(String inputArtist, String inputSong) {

        String song = inputSong;
        String artist = inputArtist;
        int indexArtist = artistHash.contain(artist);
        int indexSong = songHash.contain(song);

        if (indexArtist == -1) {

            System.out.println("|" + artist + "| does not exist "
                    + "in the artist database.");
            return;
        }

        if (indexSong == -1) {

            System.out.println(
                    "|" + song + "| does not exist " + "in the song database.");
            return;
        }


        Handle artistHandle = artistHash.retrieveHandle(indexArtist);
        Handle songHandle = songHash.retrieveHandle(indexSong);
        KVPair artistPair = new KVPair(artistHandle, songHandle);
        KVPair songPair = new KVPair(songHandle, artistHandle);

        if (tree.delete(artistPair)) {
            System.out.println("The KVPair (|" + artist + "|" + ",|" + song
                    + "|) is deleted from the tree.");
        }
        else {
            System.out.println("The KVPair (|" + artist + "|" + ",|" + song
                    + "|) was not found in the database.");
        }
        
        if (tree.delete(songPair)) {
            System.out.println("The KVPair (|" + song + "|" + ",|" + artist
                    + "|) is deleted from the tree.");
        }
        else {
            System.out.println("The KVPair (|" + song + "|" + ",|" + artist
                    + "|) was not found in the database.");
        }
        
        if (tree.firstPair(artistHandle) == null) {
            artistHash.remove(artist);
            System.out.println("|" + artist + "| is deleted "
                    + "from the artist database.");
        }
        
        if (tree.firstPair(songHandle) == null) {
            songHash.remove(song);
            System.out.println(
                    "|" + song + "| is deleted " + "from the song database.");
        }
    }

    /**
     * 
     * @param input
     *            is the input there are three different options we can print
     *            artists, songs and blocks
     */
    public void print(String input) {

        String printed = input;

        if (printed.equals("song")) {
            if (songHash.getElements() > 0) {
    
                String[] songTable = songHash.storeElements();
                int[] songIndex = songHash.storeIndex();
    
                for (int i = 0; i < songHash.getElements(); i++) {
    
                    System.out.println("|" + 
                            songTable[i] + "| " + songIndex[i]);
                }
            }
            System.out.println("total songs: " + songHash.getElements());
        }
    
        else if (printed.equals("artist")) {
            if (artistHash.getElements() > 0) {
    
                String[] artistTable = artistHash.storeElements();
                int[] artistIndex = artistHash.storeIndex();
    
                for (int i = 0; i < artistHash.getElements(); i++) {
    
                    System.out.println(
                            "|" + artistTable[i] + "| " + artistIndex[i]);
                }
            }
            System.out.println("total artists: " 
                    + artistHash.getElements());
        }
        else if (printed.equals("blocks")) {
            System.out.println(new String(pool.getList().toString()));
        }
         
        else if (printed.equals("tree")) {
            System.out.println("Printing 2-3 tree:");
            if (tree != null) {
                System.out.print(tree.toString());
            }
        }
        else {
            System.out.println("Error bad print type" + printed);
        }
        
    }

    /**
     * 
     * @param type
     *            is the input type (song | artist)
     * @param name
     *            is the name of input type
     */
    public void list(String type, String name) {

        Handle key;
        if (type.equals("artist")) {

            int indexOfArtist = artistHash.contain(name);
            if (indexOfArtist == -1) {

                System.out.println("|" + name + "| does not exist "
                        + "in the artist database.");
                return;
            }
            else {

                key = artistHash.retrieveHandle(indexOfArtist);
            }
        }
        else {

            int indexOfSong = songHash.contain(name);
            if (indexOfSong == -1) {

                System.out.println("|" + name + "| does not exist "
                        + "in the song database.");
                return;
            }
            else {

                key = songHash.retrieveHandle(indexOfSong);
            }
        }

        BaseNode node = tree.searchKey(key); 
        while (node != null && node.getLeft() != null) {

            if (key.compareTo(node.getLeft().key()) == 0) {

                System.out.println("|"
                        + new String(pool.get(node.getLeft().value())) + "|");
            }

            if (node.getRight() != null
                    && node.getRight().key().compareTo(key) == 0) {

                System.out.println("|"
                        + new String(pool.get(node.getRight().value())) + "|");
            }

            node = node.getRightSib();
        }
    }
}