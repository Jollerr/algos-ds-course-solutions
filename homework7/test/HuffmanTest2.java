import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HuffmanTest2 {
    Huffman huf;

    @BeforeAll
    void setUp() {

    }

    @Test
    public void test() {
        byte[] o = "".getBytes();
        System.out.println(Arrays.toString(o));
        huf = new Huffman(o);
        byte[] encoded = huf.encode(o);
        byte[] decoded = huf.decode(encoded);
        assertArrayEquals(o, decoded);
        assertEquals(o.length, decoded.length);
        byte b = Byte.parseByte("00000111", 2);
        System.out.println(b);
        String str = String.valueOf(b);
        System.out.println((char)b);
    }

}