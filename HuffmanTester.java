/**
 * Created by Peerachai Banyongrakkul Sec.1 5988070
 * 30/11/2017
 * HuffmanTester.java
 * @author USER
 */
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
public class HuffmanTester 
{
    // Huffman Tree for encoding and decoding.
    public static HuffmanTree hufftree;
    // Hash map with keep data and their frequency.
    public static HashMap<String, String> map = new HashMap<>();
    // Summation of each frequency.
    public static double sumFre = 0;
    // Summation of code length.
    public static double sumCodeLength = 0;
    // Avantage of code length.
    public static double avg;
    // Name of textfile which keeps original text.
    private static final String FILENAME = "textfile.txt";
    // Name of textfile which keeps encoded text. (ASCII String) 
    private static final String ENCODEDFILENAME = "Huffmancode.txt";
    
    /**
     * this method is used to generate the huffman tree by using priority queue to implement.
     * @param f
     * @return 
     */
    public static HuffmanTree buildHuffmanTree(int[] f) 
    {
        PriorityQueue<HuffmanTree> t = new PriorityQueue<>();
        for (int i = 0; i < f.length; i++)
        {
            if (f[i] > 0)
            {
                HuffmanLeaf leaf = new HuffmanLeaf(f[i], (char)i);
                t.offer(leaf);
            }
        }
 
        if(t.size()>0)
        {
            while (t.size() > 1) 
            {
                HuffmanTree a = t.poll();
                HuffmanTree b = t.poll();
                t.offer(new HuffmanNode(a, b));
            }
            return t.poll();
        }
        else
        {
            return null;
        }
    }
 
    /**
     * this recursive method is used to generate the table of frequencies and calculate average code length.
     * @param tree
     * @param prefix 
     */
    public static void generateEachCodes(HuffmanTree tree, StringBuffer prefix) 
    {
        if(tree != null)
        {
            // append code text until leaf.
            if (tree instanceof HuffmanLeaf) 
            {
                HuffmanLeaf leaf = (HuffmanLeaf)tree;
                System.out.println(leaf.getData() + "\t\t" + leaf.frequency + "\t\t" + prefix);         
                map.put(leaf.getData()+"", prefix.toString());
                sumFre += leaf.frequency;
                sumCodeLength += (leaf.frequency*(prefix.length()));
                avg = sumCodeLength/sumFre;
            } 
            else if (tree instanceof HuffmanNode) 
            {
                HuffmanNode node = (HuffmanNode)tree;

                // traverse left, append 0
                prefix.append('0');
                generateEachCodes(node.getLeft(), prefix);
                prefix.deleteCharAt(prefix.length()-1);

                // traverse right, append 1
                prefix.append('1');
                generateEachCodes(node.getRight(), prefix);
                prefix.deleteCharAt(prefix.length()-1);
            }
        }
        else
        {
            System.out.println("There is no tree.");
        }
    }
    
    /**
     * this method is used for encoding from original string to huffman code.
     * @param map
     * @param txt
     * @return 
     */
    public static String enCode(HashMap<String, String> map,String txt)
    {
        StringBuffer huffman= new StringBuffer("");
        char[] text = txt.toCharArray();
        for(int i = 0 ; i<text.length ; i++)
        {
            for (String data: map.keySet())
            {
                if(text[i] == data.charAt(0))
                {
                    String value = map.get(data);  
                    huffman.append(value);
                }
            }
        }
        String huffmancode = huffman.toString();   
        return huffmancode;
    }
        
    /**
     * this method is used for converting binary code to ASCII string.
     * @param str
     * @return 
     */
    public static String huffToASCIIchar(String str)
    {
        String string = "";
        for (int i = 0; i < str.length()/8; i++) {

            int a = Integer.parseInt(str.substring(8*i,(i+1)*8),2);
            string += (char)(a);
        }
        return string;
    }
    
    
    /**
     * this recursive method is used for decoding huffman code back to original string.
     * @param huffcode
     * @param tree 
     * @param out 
     */
    public static void deCode(String huffcode,HuffmanTree tree,StringBuffer out)
    {
        StringBuffer output = out;
        if(!huffcode.isEmpty())
        {
            // traverse until leaf to get the data, and append output with it.
            if(tree instanceof HuffmanLeaf)
            {
               HuffmanLeaf leaf = (HuffmanLeaf) tree;
               output.append(leaf.getData());
               deCode(huffcode,hufftree,output);
            }
            else if(tree instanceof HuffmanNode)
            {
                HuffmanNode node = (HuffmanNode) tree;
                if(huffcode.charAt(0) == '1')
                {
                    huffcode = huffcode.substring(1);
                    deCode(huffcode,node.getRight(),out);
                }
                else
                {
                    huffcode = huffcode.substring(1);
                    deCode(huffcode,node.getLeft(),out);
                }
            }
        }
        else
        {
            //for appending output with last word and print it out
            HuffmanLeaf leaf = (HuffmanLeaf) tree;
            output.append(leaf.getData());
            System.out.println(output);
        }
    }
    
  
    /**
     * MAIN for Tester
     * @param args 
     */
    public static void main(String[] args) {
        
/*************************** ENCODING PART *****************************/
        // Read text file and get original text
        String content = new String();
        BufferedReader br = null;
        FileReader fr = null;
        try {
                br = new BufferedReader(new FileReader(FILENAME));
                String sCurrentLine;
                System.out.println("ORIGINAL TEXT: ");
                while ((sCurrentLine = br.readLine()) != null) 
                {
                    content += sCurrentLine;
                    System.out.print(sCurrentLine);
                }
                System.out.println();
                System.out.println();
        } 
        catch (IOException e) {} 
        finally 
        {
            try 
            {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {}
        }
 
        int[] charFreqs = new int[10000];
        
        // read each character and record the frequencies
        for (char c : content.toCharArray())
        {
            charFreqs[c]++;
        }
 
        // build huffman tree
        hufftree = buildHuffmanTree(charFreqs);
 
        // print out results as a table of characters ,their frequencies, and their huffman code.
        System.out.println("FREQUENCIES & HUFFMANCODE Table: ");
        System.out.println("Char\t\tFrequency\tHuffman Code");
        generateEachCodes(hufftree, new StringBuffer());   
        System.out.println();
        
        // encode, convert to ASCII character, and print it out
        System.out.println("HUFFMAN CODE of TEXT:");
        System.out.println(enCode(map,content));
        String huffman = enCode(map,content);
        System.out.println();
        System.out.println("ASCII String: ");
        System.out.println(huffToASCIIchar(huffman));
        String huffmancode = huffToASCIIchar(huffman);
     
        // print average code length 
        System.out.println();
        System.out.println("AVERAGE CODE LENGTH: ");
        System.out.println(avg);
        
        // write huffman code (ASCII character) to text file.
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(ENCODEDFILENAME));
            writer.write(huffmancode);
        }
        catch ( IOException e){}
        finally
        {
            try
            {
                if ( writer != null)
                writer.close( );
            }
            catch ( IOException e){}
        }
        
        /*************************** DECODING PART *****************************/
        
        // read file huffman code (ASCII String)
        String contentt = new String();
        BufferedReader brr = null;
        FileReader frr = null;
        try {
                brr = new BufferedReader(new FileReader(ENCODEDFILENAME));
                String sCurrentLine;
                while ((sCurrentLine = brr.readLine()) != null) 
                {
                    contentt += sCurrentLine;
                }
        } 
        catch (IOException e) {} 
        finally 
        {
            try 
            {
                if (brr != null)
                    brr.close();
                if (frr != null)
                    frr.close();
            } catch (IOException ex) {}
        }
        
        System.out.println();
        
        // decode and print it out
        System.out.println("Decode from coded file: ");
        StringBuffer output = new StringBuffer("");
        deCode(huffman,hufftree,output);

    }
}