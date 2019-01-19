import static org.junit.Assert.*;
import org.junit.Test;

import java.util.List;


// implements iterator
public class NodeTestKK {


    @Test
    public void addChild() {
        Node A = new Node("A", null,null);
        Node B = new Node("B", null,null);
        Node C = new Node("C", null,null);
        Node D = new Node("D", null,null);
        A.addChild(B);
        A.addChild(C);
        A.addChild(D);
        StringBuilder sb = new StringBuilder(A.toString());
        sb.append(A.getFirstChild().toString());
        Node testRun = A.getFirstChild();
        while (testRun != null) {
            testRun = testRun.getNextSibling();
            sb.append(testRun);
        }
        assertEquals("Expect nodes to be connected","ABCDnull", sb.toString());
    }

    @Test
    public void stringGetChildrenAsStringList() {
        String s = "((G,H)D,E,(I)F)A";
        List<String> list = Node.stringGetChildrenAsStringList(s);
        assertEquals(3, list.size());
        StringBuilder sb = new StringBuilder();
        for (String str : list) sb.append(str);
        assertEquals("(G,H)DE(I)F", sb.toString());
    }

    @Test
    public void stringGetChildAsString() {
        String s = "(G,H)D,E,(I)F";
        assertEquals("(G,H)D", Node.stringGetChildAsString(s));
    }

    @Test
    public void parseCustom_testRoot() {
        String s = "((G,H)D,E,(I)F)A";
        Node root = Node.parsePostfix(s);
        assertEquals("A", root.toString());

        assertEquals("D", root.getFirstChild().toString());
        assertEquals("G", root.getFirstChild().getFirstChild().toString());

    }



    @Test (timeout = 1000)
    public void leftParentheticRepresentation() {
        String s = "((G,H)D,E,(I)F)A";
        Node root = Node.parsePostfix(s);
        assertEquals("A(D(G,H),E,F(I))", root.leftParentheticRepresentation());
    }
}