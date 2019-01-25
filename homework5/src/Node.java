import java.util.*;

public class Node implements java.util.Iterator<Node> {

   public static void main (String[] param) {
      String s = "((A)B,((C,)D)E"; //  "((A),(B)C)D" , "((A)B(C)D)E" , "((A)(C)D)E"
      Node t = Node.parsePostfix (s);
      String v = t.leftParentheticRepresentation();
      System.out.println (s + " ==> " + v); // (B1,C)A ==> A(B1,C)
   }

   private String name;
   private Node firstChild, nextSibling;

   Node (String n, Node d, Node r) throws IllegalArgumentException {
      if (!isValidName(n)) {
         throw new IllegalArgumentException("Illegal node name: '" + n + "'. Node name must be non-empty and must not contain round brackets, commas and whitespace symbols.");
      }
      name = n;
      firstChild = d;
      nextSibling = r;
   }

   @Override
   public boolean hasNext() {
      return getNextSibling() != null;
   }

   @Override
   public Node next() {
      return getNextSibling();
   }

   @Override
   public String toString() {
      return name;
   }

   public boolean hasFirstChild() {
      return getFirstChild() != null;
   }

   public static boolean isValidName(String name) {
      if (name.isEmpty()) return false;
      HashSet<Character> illegalChars = new HashSet<>(Arrays.asList('(', ')', ',', '\t', ' ')); // https://stackoverflow.com/questions/5654144/add-multiple-fields-to-java-5-hashset-at-once
      for (int i = 0; i < name.length(); i++) {
         if (illegalChars.contains(name.charAt(i))) return false;
      }
      return true;
   }

   public Node getFirstChild() {
      return firstChild;
   }

   public Node getNextSibling() {
      return nextSibling;
   }

   public void setFirstChild(Node firstChild) {
      this.firstChild = firstChild;
   }

   public void setNextSibling(Node nextSibling) {
      this.nextSibling = nextSibling;
   }

   public void addChild(Node child) {
      if (!hasFirstChild()) {
         setFirstChild(child);
         return;
      }
      addSiblingRecursively(getFirstChild(), child);
   }

   public void addSiblingRecursively(Node sibling, Node newSibling) {
      if (sibling.hasNext()) addSiblingRecursively(sibling.getNextSibling(), newSibling); else sibling.setNextSibling(newSibling);
   }

   public static boolean stringHasChildren(String s) throws IllegalArgumentException {
      // e.g. if (child1,child2)name
      int startIndex = s.indexOf('(');
      int endIndex = s.lastIndexOf(')');
      if (startIndex == 0 && endIndex  != -1) {
         if (endIndex - startIndex < 2) {
            throw new IllegalArgumentException("Problem in " + s + ". Parent has parentheses but no children inside.");
         }
         return  true;
      }
      return false;
   }

   public static List<String> stringGetChildrenAsStringList(String s) throws IllegalArgumentException {
      s = stringRemoveParentNotationParentheses(s); // ((...)child1,(...)child2)parent -> (...)child1,(...)child2
      List<String> list = new ArrayList<>();
      while (!s.isEmpty()) {
         String child = Node.stringGetChildAsString(s);
         list.add(child);
         s = s.substring(child.length()); //removes the found child from the string
         if (s.indexOf(',') == 0) s = s.substring(1); // removes leftover ',' from the previous child
      }
      return list;
   }

   public static String stringRemoveParentNotationParentheses(String s) {
       return s.substring(s.indexOf('(') + 1, s.lastIndexOf(')'));
   }

   public static String stringGetChildAsString(String s) {
      int count = 0; // count == 0 -> child, otherwise might be child of a child when separator ',' met
      int i = 0; // char pointer
      StringBuilder sb = new StringBuilder();
      loop: while (i < s.length()) {
         char c;
         switch (c = s.charAt(i++)) {
            case ',': if (count == 0) break loop;
               break;
            case '(': count++;
               break;
            case ')': count--;
         }
         sb.append(c);
      }
      return sb.toString();
   }

   public static Node parsePostfix (String s) {
      try {
         return parsePostFixImpl(s);
      } catch (IllegalArgumentException e) {
         throw new RuntimeException("Couldn't parse " + s + ". " + e.getMessage());
      }
   }

   private static Node parsePostFixImpl(String s) {
         String nodeName;
         Node currentNode;
         if (stringHasChildren(s)) {
            nodeName = s.substring(s.lastIndexOf(')') + 1);
         } else {
            currentNode = new Node(s, null, null); // node has no children
            return currentNode;
         }
         currentNode = new Node (nodeName, null, null);
         for (String childAsString : Node.stringGetChildrenAsStringList(s)) {
            currentNode.addChild(parsePostFixImpl(childAsString));
         }
         return currentNode;
   }

   public String leftParentheticRepresentation() {
      return traverse(this);
   }

   private String traverse(Node current) {
      // kui pole last ega sugulasi, tagastab nime
      if (!current.hasFirstChild() && !current.hasNext()) return current.toString();
      // kui pole sugulasi, siis avab sulud, sulgude sees on laps, sulud kinni
      if (current.hasFirstChild() && !current.hasNext()) return current + "(" + traverse(current.getFirstChild()) + ")";
      // kui pole last, siis komaga eraldab sugulase
      if (!current.hasFirstChild() && current.hasNext()) return current + "," + traverse(current.getNextSibling());
      // kui ikka peab kõike olema...
      return current + "(" + traverse(current.getFirstChild()) + ")," + current.traverse(current.getNextSibling());
   }
}