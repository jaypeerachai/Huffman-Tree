/**
 * Created by Peerachai Banyongrakkul Sec.1 5988070
 * 30/11/2017
 * HuffmanNode.java
 * @author USER
 */
public class HuffmanNode extends HuffmanTree 
{
    private final HuffmanTree left;
    private final HuffmanTree right;
 
    /**
     * constructor
     * @param left
     * @param right 
     */
    public HuffmanNode(HuffmanTree left, HuffmanTree right) 
    {
        super(left.frequency + right.frequency);
        this.left = left;
        this.right = right;
    }
    
    public HuffmanTree getLeft()
    {
        return this.left;
    }
    
    public HuffmanTree getRight()
    {
        return this.right;
    }
}