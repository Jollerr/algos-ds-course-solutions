import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.junit.Test;

/** Testklass.
 * @author jaanus
 */
public class GraphTaskTest {

   GraphTask.Graph g;
   @Before
   public void setup() {
      g = new GraphTask().new Graph("g");
   }

   @Test (timeout=20000)
   public void graph_deepCopyTest1() throws CloneNotSupportedException {
      int n = 500;


      while (n < 2500) {
         int m = n * (n - 1) / 2;
         g.createRandomSimpleGraph(n, m);
         long startTime = System.currentTimeMillis();
         GraphTask.Graph gClone = g.deepCopy();
         System.out.println("Aega kulus " + n + " tipuga " + "ja " + m + " servaga: " + (System.currentTimeMillis() - startTime));
         n += 500;
         GraphTask.Vertex v0 = g.getFirst();
         GraphTask.Vertex v1 = gClone.getFirst();
         while (v0 != null) {
            if (v0 == v1) {
               throw new IllegalStateException("Kloonitud tipud ei tohiks viidata samale mäluaadressile.");
            }
            if (!v0.equals(v1)) {
               throw new IllegalStateException("Kloonitud tipud ei tohiks olla erinevate isendimuutujatega.");
            }
            GraphTask.Arc a0 = v0.getFirst();
            GraphTask.Arc a1 = v1.getFirst();
            while (a0 != null) {
               if (a0 == a1) {
                  throw new IllegalStateException("Kloonitud kaared ei tohiks viidata samale mäluaadressile.");
               }
               if (!a0.equals(a1)) {
                  throw new IllegalStateException("Kloonitud kaared ei tohiks olla erinevate isendimuutujatega.");
               }
               a0 = a0.getNext();
               a1 = a1.getNext();
            }
            v0 = v0.getNext();
            v1 = v1.getNext();
         }
      }
   }


}

