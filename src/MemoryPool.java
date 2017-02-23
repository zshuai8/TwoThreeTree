import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 
 * @author shuaicheng zhang
 * @version 2016/9/10
 *
 */
public class MemoryPool {
    
    private byte[] pool;
    private int poolSize;
    private int defaultSize;
    private DLList list;
    private boolean sizeCheck = false;
    private int expansionCounter;
    
/**
 *@param size is the input size for memory pool
 * initialize pool size, list, 
 * expandedsize for storing expanded poolsize when pool gets expanded
 * (could be multiple times)
 * default size is the size we want the pool to expand with
 * 
 * expansioncounter is used to track down the next follwing size 
 * pool should have
 * 
 */
    public MemoryPool(int size) {
        
        poolSize = size;
        defaultSize = size;
        expansionCounter = 1;
        pool = new byte[poolSize];
        list = new DLList(size);
        list.setPoolSize(poolSize);
    }
    
/**
 * getter method for getting list
 * @return the list which is linked to the memory pool
 */

    public DLList getList() {
        
        return list;
    }
    
/**
 * 
 * @param space is the input byte array
 * @param size is the size of the byte array
 * @return a handle which stores the value of the stored bytes
 */

    public Handle insert(byte[] space, int size) {
        
        if (size > 65536) {
            
            return null;
        }
            
        byte[] lenb = new byte[2];
        ByteBuffer lenbb = ByteBuffer.wrap(lenb);
        int len = size;
        lenbb.putShort(0, (short)len); 
        
        int index = bestfit(size);
        if (index != -1) {
            
            System.arraycopy(lenbb.array(), 0, pool, index, 2);
        }
        else {
             
            while (index == -1) {
                
                expandPool();
                sizeCheck = true;
                index = bestfit(size);
            }
            System.arraycopy(lenbb.array(), 0, pool, index, 2);
        }
        
        System.arraycopy(space, 0, pool, index + 2, size);
        list.replaceForAdd(index, size);
        
        Handle newHandle = new Handle(index);
        return newHandle;
    }
    
    /**
     * check if the pool needs to be expanded
     */
    private void expandPool() {
        
        expansionCounter++;
        int newSize = defaultSize * expansionCounter;
        byte[] newPool = new byte[newSize];
        System.arraycopy(pool, 0, newPool, 0, poolSize);
        pool = newPool;
        list.expandedList(poolSize, defaultSize);
        poolSize = newSize;
        list.setPoolSize(poolSize);
        System.out.println("Memory pool expanded to be " + poolSize
                + " bytes.");
    }

    /**
     * 
     * @param size is the input size
     * @return the best index for the given size in the pool
     * return -1 if there is no place found
     */
    private int bestfit(int size) {
        
        return list.bestfitP(size);
    }
    
    /**
     * 
     * @param theHandle is the input handle
     * remove the stored bytes in the pool
     */
    public void remove(Handle theHandle) {
        
        byte[] buffer = Arrays.copyOfRange(pool,
                theHandle.pos(), theHandle.pos() + 2);
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        int length = byteBuffer.getShort(); 
        list.replaceForRemove(theHandle.pos(), length);
        
        for (int i = 0; i < length + 2; i++) {
            
            pool[theHandle.pos() + i] = 0;
        }
    }

    
    /**
     * 
     * @param input is the input handle
     * @return a string 
     */
    public byte[] get(Handle input) {
        
        
        byte[] buffer = Arrays.copyOfRange(pool,
                input.pos(), input.pos() + 2);
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        int length = byteBuffer.getShort();
        
        byte[] output = Arrays.copyOfRange(pool,
                input.pos() + 2, input.pos() + 2 + length);
        //String outputString = new String(output);
        
        return output;
    }
    
    /**
     * 
     * @return true if the memory pool is doubled
     */
    public boolean poolDoubled() {
        
        if (sizeCheck) {
            
            sizeCheck = false;
            return true;
        }
        
        return sizeCheck ;
    }
    
    /**
     * 
     * @return the pool size
     */
    public int size() {
        
        return poolSize;
    }
}


