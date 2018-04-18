/**
 * Created by Peerachai Banyongrakkul Sec.1 5988070
 * 30/11/2017
 * HuffmanTree.java
 * @author USER
 */
abstract class HuffmanTree implements Comparable<HuffmanTree> 
{
    // the frequency of this tree
    public final int frequency;
    
    /**
     * constructor
     * @param freq 
     */
    public HuffmanTree(int freq) 
    { 
        this.frequency = freq; 
    }
 
    /**
     * compares on the frequency
     * @param tree
     * @return 
     */
    @Override
    public int compareTo(HuffmanTree tree) 
    {
        return frequency - tree.frequency;
    }
}