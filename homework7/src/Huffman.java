import java.util.*;

/**
 * Prefix codes and Huffman tree.
 * Tree depends on source data.
 * Kasutatud allikad:   http://enos.itcollege.ee/~ylari/I231/Huffman.java
 *                      https://www.geeksforgeeks.org/huffman-coding-greedy-algo-3/
 */
public class Huffman {

    private Map<Byte, HufNode> map;
    private int bitLength;
    private HufNode root;

    class HufNode implements Comparable<HufNode> {
        private HufNode left;
        private HufNode right;
        private Byte aByte;
        private int frequency;
        private String binCode;

        public HufNode(byte aByte) {
            this(null, null);
            this.aByte = aByte;
        }

        public HufNode(HufNode left, HufNode right) {
            this.left = left;
            this.right = right;
            if (left != null && right != null) {
                frequency = left.frequency + right.frequency;
            } else {
                frequency = 1;
            }
            binCode = null;
            aByte = null;
        }

        @Override
        public int compareTo(HufNode o) {
            return frequency - o.frequency;
        }
    }

    /** Constructor to build the Huffman code for a given bytearray.
     * @param original source data
     */
    Huffman (byte[] original) {
        if (original.length == 0) throw new IllegalArgumentException("Cannot create Huffman code from a empty string.");
        map = createAndGetMap(original);
        root = initialize(original);
    }

    /** Length of encoded data in bits.
     * @return number of bits
     */
    public int bitLength() {
        return bitLength; //
    }


    /** Encoding the byte array using this prefixcode.
     * @param origData original data
     * @return encoded data
     */
    public byte[] encode (byte [] origData) {
        if (origData.length == 0) throw new IllegalArgumentException("Cannot encode an empty string.");
        StringBuilder sb = new StringBuilder();
        for (byte b : origData) {
            sb.append(map.get(b).binCode);
        }
        bitLength = sb.length();
        byte[] encoded;
        // figure out the size of the new array
        if (bitLength % 8 == 0) {
            encoded = new byte[bitLength / 8];
        } else {
            encoded = new byte[bitLength / 8 + 1];
        }
        int i = 0;
        while (sb.length() > 0) {
            while (sb.length() < 8) {
                sb.append(0);
            }
            encoded[i++] = (byte) Integer.parseInt(sb.substring(0, 8), 2);
            sb.delete(0, 8);
        }
        return encoded;
    }

    /** Decoding the byte array using this prefixcode.
     * @param encodedData encoded data
     * @return decoded data (hopefully identical to original)
     */
    public byte[] decode (byte[] encodedData) {
        if (encodedData.length == 0) throw new IllegalArgumentException("Cannot decode an empty string.");
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (;i < encodedData.length; i++) {
            sb.append(String.format("%8s", Integer.toBinaryString(encodedData[i] & 0xFF)).replace(' ', '0'));
        }
        sb.replace(bitLength, sb.length(), "");
        List<Byte> list = new ArrayList<>();
        while (sb.length() > 0) {
            list.add(travelTreeAndDecode(sb, root));
        }
        byte[] decoded = new byte[list.size()];
        int j = 0;
        for (Byte b : list) {
            decoded[j++] = b;
        }
        return decoded;
    }

    public HufNode initialize(byte[] arr) {
        PriorityQueue<HufNode> q = new PriorityQueue<>();
        // add nodes to queue
        for (HufNode node : map.values()) {
            q.add(node);
        }
        HufNode root = createMinHeapTreeAndReturnRoot(q);
        traverseTreeAndSetCodesForNodes(root, new StringBuilder());
        return root;
    }
    public Map<Byte, HufNode> createAndGetMap(byte[] arr) {
        Map<Byte, HufNode> map = new HashMap<>();
        for (byte b : arr) {
            HufNode node;
            if ((node = map.get(b)) != null) {
                node.frequency++;
            } else {
                map.put(b, new HufNode(b));
            }
        }
        return map;
    }

    public HufNode createMinHeapTreeAndReturnRoot(PriorityQueue<HufNode> q) {
        while (q.size() > 1) {
            q.add(new HufNode(q.poll(), q.poll()));
        }
        return q.poll();
    }

    public void traverseTreeAndSetCodesForNodes(HufNode current, StringBuilder prefix) {
        if (isLeaf(current)) {
            if (prefix.length() > 0) {
                current.binCode = prefix.toString();
            } else {
                current.binCode = "0";
            }
        } else {
            prefix.append(0);
            traverseTreeAndSetCodesForNodes(current.left, prefix);
            prefix.deleteCharAt(prefix.length() - 1); // cleaning after coming back from the call stack
            //
            prefix.append(1);
            traverseTreeAndSetCodesForNodes(current.right, prefix);
            prefix.deleteCharAt(prefix.length() - 1); // cleaning after coming back from the call stack
        }
    }

    public byte travelTreeAndDecode(StringBuilder sb, HufNode current) {
        if (isLeaf(current)) {
            if (map.size() < 2) { // vastasel kui Hufnode on ainult 1, siis ta ei hakka liikuma ja ei kustuta tähti
                sb.deleteCharAt(0);
            }
            return current.aByte;
        } else {
            char c = sb.charAt(0);
            sb.deleteCharAt(0);
            if (c == '0') {
                return travelTreeAndDecode(sb, current.left);
            } else {
                return travelTreeAndDecode(sb, current.right);
            }
        }
    }

    public boolean isLeaf(HufNode node) {
        return node.left == null && node.right == null;
    }

    /** Main method. */
    public static void main (String[] params) {
        String tekst = "AAAA3215AAA#%#&¤BBBB46BCCCDE";
        byte[] orig = tekst.getBytes();
        Huffman huf = new Huffman (orig);
        byte[] kood = huf.encode (orig);
        byte[] orig2 = huf.decode (kood);
        // must be equal: orig, orig2
        System.out.println (Arrays.equals (orig, orig2));
        int lngth = huf.bitLength();
        System.out.println ("Length of encoded data in bits: " + lngth);
        // TODO!!! Your tests here!
        // TODO!!! Your tests here!

    }
}