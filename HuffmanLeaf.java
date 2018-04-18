/**
 * Created by Peerachai Banyongrakkul Sec.1 5988070
 * 30/11/2017
 * HuffmanLeaf.java
 * @author USER
 */
public class HuffmanLeaf extends HuffmanTree 
{
    private final char data;
 
    /**
     * constructor
     * @param freq
     * @param val 
     */
    public HuffmanLeaf(int freq, char val) 
    {
        super(freq);
        this.data = val;
    }
    
    public char getData()
    {
        return this.data;
    }
}