import javafx.scene.shape.Arc;
import sun.security.provider.certpath.Vertex;

import java.util.*;

/** Container class to different classes, that makes the whole
 * set of classes one class formally.
 */
public class GraphTask {

   /** Main method. */
   public static void main (String[] args) throws CloneNotSupportedException {
      GraphTask a = new GraphTask();
      a.run();
   }

   /** Actual main method to run examples and everything. */
   public void run() throws CloneNotSupportedException {
//      /*
//       * Kontrolli Vertexi equals meetodit erinevate konstruktoritega
//       */
//      Vertex v1 = new Vertex("v1");
//      Vertex v2 = new Vertex("v1");
//      System.out.println("Peaks tagastama true, tagastab: " + v1.equals(v2));
//      //
//      v1 = new Vertex("v1", null, null);
//      v2 = new Vertex("v1", new Vertex("v1"), new Arc("a1"));
//      System.out.println("Peaks tagastama true, tagastab: " + v1.equals(v2));
//      //
//      v1 = new Vertex("v1");
//      v2 = new Vertex("v2");
//      System.out.println("Peaks tagastama false, tagastab: " + v1.equals(v2));
//      //
//      v1 = new Vertex("v1", null, null);
//      v2 = new Vertex("v101", new Vertex("v1"), new Arc("a1"));
//      System.out.println("Peaks tagastama false, tagastab: " + v1.equals(v2));
//      /*
//       * Kontrolli Arc equals meetodit erinevate konstruktoritega
//       */
//      Arc a1 = new Arc("a1");
//      Arc a2 = new Arc("a1");
//      try {
//         a1.equals(a2);
//      } catch (NullPointerException e) {
//         System.out.println("Viskab erindi NullPointerExceptioni, kuna target on null väärtusega kaartel");
//      }
//      //
//      a1 = new Arc("a1", new Vertex("v1"), null);
//      a2 = new Arc("a1", new Vertex("v1"), new Arc("a3"));
//      System.out.println("Peaks tagastama true, tagastab: " + a1.equals(a2));
//      //
//      a1 = new Arc("a1", new Vertex("v3"), null);
//      a2 = new Arc("a101", new Vertex("v1"), new Arc("a1"));
//      System.out.println("Peaks tagastama false, tagastab: " + a1.equals(a2));
//      /*
//       * Kontrolli meetodi deepCopy() toimivust
//       */
//      Graph g = new Graph("g");
//      g.createRandomSimpleGraph(50,1225);
//      GraphTask.Graph gClone = g.deepCopy();
//      GraphTask.Vertex v0 = g.getFirst();
//      GraphTask.Vertex v1 = gClone.getFirst();
//      while (v0 != null) {
//         if (v0 == v1) {
//            throw new IllegalStateException("Kloonitud tipud ei tohiks viidata samale mäluaadressile.");
//         }
//         if (!v0.equals(v1)) {
//            throw new IllegalStateException("Kloonitud tipud ei tohiks olla erinevate isendimuutujatega.");
//         }
//         GraphTask.Arc a0 = v0.getFirst();
//         GraphTask.Arc a1 = v1.getFirst();
//         while (a0 != null) {
//            if (a0 == a1) {
//               throw new IllegalStateException("Kloonitud kaared ei tohiks viidata samale mäluaadressile.");
//            }
//            if (!a0.equals(a1)) {
//               throw new IllegalStateException("Kloonitud kaared ei tohiks olla erinevate isendimuutujatega.");
//            }
//            a0 = a0.getNext();
//            a1 = a1.getNext();
//         }
//         v0 = v0.getNext();
//         v1 = v1.getNext();
//      }
//
//      /*
//       * Kontrolli meetodi isIsomorphicTo() toimivust
//       */
//      if (!g.isIsomorphicTo(gClone)) {
//         throw new IllegalStateException("Meetod isIsomorphicTo() peaks tagastama true väärtuse");
//      }
//
//      /*
//       * Mõõda meetodi isIsomorphicTo aega
//       */
//      Graph g = new GraphTask().new Graph("g");
//      int n = 500;
//      while (n <= 2500) {
//         int m = n * (n - 1) / 2;
//         g.createRandomSimpleGraph(n, m);
//         GraphTask.Graph gClone = g.deepCopy();
//         long startTime = System.currentTimeMillis();
//         gClone.isIsomorphicTo(g);
//         System.out.println("Aega kulus " + n + " tipuga " + "ja " + m + " servaga: " + (System.currentTimeMillis() - startTime) + " ms");
//         n += 500;
//      }
   }

   // TODO!!! add javadoc relevant to your problem
   class Vertex  {

      private String id;
      private Vertex next;
      private Arc first;
      private int info = 0;

      Vertex (String s, Vertex v, Arc e) {
         id = s;
         next = v;
         first = e;
      }

      Vertex (String s) {
         this (s, null, null);
      }

      // TODO!!! Your Vertex methods here!

      @Override
      public String toString() {
         return id;
      }

      /** Võrdle tippe
       * @param o võrreldav tipp
       * @return true, kui isendimuutujad 'id' on samased
       */
      @Override
      public boolean equals(Object o) {
         if (this == o) return true;
         if (!(o instanceof Vertex)) return false;

         Vertex vertex = (Vertex) o;

         return id != null ? id.equals(vertex.id) : vertex.id == null;
      }

      /** Tagasta kaarteahela esimene kaar
       * @return esimene kaar
       */
      public Arc getFirst() {
         return first;
      }

      @Override
      public int hashCode() {
         return id != null ? id.hashCode() : 0;
      }

      /** Tagasta järgmine tipp
       * @return järgmine tipp
       */
      public Vertex getNext() {
         return next;
      }
   }


   /** Arc represents one arrow in the graph. Two-directional edges are
    * represented by two Arc objects (for both directions).
    */
   class Arc  {

      public String id;
      public Vertex target;
      public Arc next;
      public int info = 0;

      Arc (String s, Vertex v, Arc a)  {
         id = s;
         target = v;
         next = a;
      }

      Arc (String s) {
         this (s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }

      // TODO!!! Your Arc methods here!

      /** Tagasta järgmine kaar
       * @return järgmine kaar
       */
      public Arc getNext() {
         return next;
      }

      /** Võrdle kaari. Ei arvesta lähtetippu
       * @param o võrreldav kaar
       * @return true, kui isendimuutujad 'target' ja 'id' on samased
       */
      @Override
      public boolean equals(Object o) {
          if (this == o) return true;
          if (!(o instanceof Arc)) return false;

          Arc arc = (Arc) o;

          if (!id.equals(arc.id)) return false;
          return target.equals(arc.target);
      }

       @Override
       public int hashCode() {
           int result = id.hashCode();
           result = 31 * result + target.hashCode();
           return result;
       }
   }


   class Graph implements java.lang.Cloneable {

      private String id;
      private Vertex first; // graafi algus
      private int info = 0;
      // You can add more fields, if needed

      Graph (String s, Vertex v) {
         id = s;
         first = v;
      }

      Graph (String s) {
         this (s, null);
      }

      @Override
      public String toString() {
         String nl = System.getProperty ("line.separator");
         StringBuffer sb = new StringBuffer (nl);
         sb.append (id);
         sb.append (nl);
         Vertex v = first;
         while (v != null) {
            sb.append (v.toString());
            sb.append (" -->");
            Arc a = v.first;
            while (a != null) {
               sb.append (" ");
               sb.append (a.toString());
               sb.append (" (");
               sb.append (v.toString());
               sb.append ("->");
               sb.append (a.target.toString());
               sb.append (")");
               a = a.next;
            }
            sb.append (nl);
            v = v.next;
         }
         return sb.toString();
      }

      public Vertex createVertex (String vid) {
         Vertex res = new Vertex (vid);
         res.next = first;
         first = res;
         return res;
      }

      private Arc createArc (String aid, Vertex from, Vertex to) {
         Arc res = new Arc (aid);
         res.next = from.first;
         from.first = res;
         res.target = to;
         return res;
      }

      /**
       * Create a connected undirected random tree with n vertices.
       * Each new vertex is connected to some random existing vertex.
       * @param n number of vertices added to this graph
       */
      public void createRandomTree (int n) {
         if (n <= 0)
            return;
         Vertex[] varray = new Vertex [n];
         for (int i = 0; i < n; i++) {
            varray [i] = createVertex ("v" + String.valueOf(n-i));
            if (i > 0) {
               int vnr = (int)(Math.random()*i);
               createArc ("a" + varray [vnr].toString() + "_"
                  + varray [i].toString(), varray [vnr], varray [i]);
               createArc ("a" + varray [i].toString() + "_"
                  + varray [vnr].toString(), varray [i], varray [vnr]);
            } else {}
         }
      }

      /**
       * Create an adjacency matrix of this graph.
       * Side effect: corrupts info fields in the graph
       * @return adjacency matrix
       */
      public int[][] createAdjMatrix() {
         info = 0;
         Vertex v = first;
         while (v != null) {
            v.info = info++;
            v = v.next;
         }
         int[][] res = new int [info][info];
         v = first;
         while (v != null) {
            int i = v.info;
            Arc a = v.first;
            while (a != null) {
               int j = a.target.info;
               res [i][j]++;
               a = a.next;
            }
            v = v.next;
         }
         return res;
      }

      /**
       * Create a connected simple (undirected, no loops, no multiple
       * arcs) random graph with n vertices and m edges.
       * @param n number of vertices
       * @param m number of edges
       */
      public void createRandomSimpleGraph (int n, int m) {
         if (n <= 0)
            return;
         if (n > 2500)
            throw new IllegalArgumentException ("Too many vertices: " + n);
         if (m < n-1 || m > n*(n-1)/2)
            throw new IllegalArgumentException 
               ("Impossible number of edges: " + m);
         first = null;
         createRandomTree (n);       // n-1 edges created here
         Vertex[] vert = new Vertex [n];
         Vertex v = first;
         int c = 0;
         while (v != null) {
            vert[c++] = v;
            v = v.next;
         }
         int[][] connected = createAdjMatrix();
         int edgeCount = m - n + 1;  // remaining edges
         while (edgeCount > 0) {
            int i = (int)(Math.random()*n);  // random source
            int j = (int)(Math.random()*n);  // random target
            if (i==j) 
               continue;  // no loops
            if (connected [i][j] != 0 || connected [j][i] != 0) 
               continue;  // no multiple edges
            Vertex vi = vert [i];
            Vertex vj = vert [j];
            createArc ("a" + vi.toString() + "_" + vj.toString(), vi, vj);
            connected [i][j] = 1;
            createArc ("a" + vj.toString() + "_" + vi.toString(), vj, vi);
            connected [j][i] = 1;
            edgeCount--;  // a new edge happily created
         }
      }

      // TODO!!! Your Graph methods here! Probably your solution belongs here.

      /** Sügavkloneeri graaf
       * @return graafi sügavkloon
       * @throws CloneNotSupportedException
       */
      public Graph deepCopy() throws CloneNotSupportedException {
         Graph gClone = new Graph(id);
         if (first == null) return gClone;
         Map<Vertex, Vertex> vertices = oldAndCloneVertices(first); // hashmap, et kaartele panna targetid hiljem
         gClone.first = vertices.get(first);
         Vertex v = first;
         while (v != null) {
            if (v.first == null) continue;
            vertices.get(v).first = createAndConnectCloneArcs(v.first, vertices);
            v = v.getNext();
         }
         return gClone;
      }

      /** Loo vanade tippude ahela sügavkloon
       * @param first sügavkloneeritava graafi esimene tipp
       * @return  paisktabel, kus: võti - kloneeritav tipp, väärtus - sügavkloonitud tipu koopia ilma kaarteta
       */
      private Map<Vertex, Vertex> oldAndCloneVertices(Vertex first) {
         Map<Vertex, Vertex> vertices = new HashMap<>();
         Vertex oldV = first;
         Vertex newV = new Vertex(oldV.id);
         vertices.put(oldV, newV);
         while (oldV.next != null) {
            oldV = oldV.next;
            newV.next = new Vertex(oldV.id);
            vertices.put(oldV, newV.next);
            newV = newV.next;
         }
         return vertices;
      }

      /** Loo vana kaarteahela sügavkloon
       * @param first sügavkloneeritava kaarte ahela esimene element
       * @param vertices   paisktabel, kus võti - kloneeritav tipp, väärtus - sügavkloonitud koopia
       * @return  kaarte ahela esimene element
       */
      private Arc createAndConnectCloneArcs(Arc first, Map<Vertex, Vertex> vertices) {
         Arc oldA = first;
         Arc newA = new Arc(oldA.id, vertices.get(oldA.target), null);
         Arc newFirst = newA;
         while (oldA.next != null) {
            oldA = oldA.next;
            newA.next = new Arc(oldA.id, vertices.get(oldA.target), null);
            newA = newA.next;
         }
         return newFirst;
      }

      /** Kontrolli kahe graafi isomorfismi
       * @param g võrreldav graaf
       * @return  true, kui graafidel on tipud, kaared ja nendevahelised seosed samased
       */
      public boolean isIsomorphicTo(Graph g) {
          if (!hasSameVertices(g)) return false;
          if (!hasSameArcs(g)) return false;
          return true;
      }

      /** Võrdle graafide tippe
       * @param g võrreldav graaf
       * @return true, kui tippe on sama palju ja samased (equals)
       */
      private boolean hasSameVertices(Graph g) {
          HashSet<Vertex> thisGraphVertices = new HashSet<>();
          Vertex v = first;
          while (v != null) {
              thisGraphVertices.add(v);
              v = v.next;
          }
          int n = thisGraphVertices.size();
          v = g.first;
          // kontrollib, kas tipud on samad
          while (v != null) {
              if (thisGraphVertices.contains(v)) n--; else return false;
              v = v.next;
          }
          if (n > 0) return false; // kui jääb üle, siis ei ole võrdsed
          return true;
      }

      /** Võrdle graafide kaari
       * @param g võrreldav graaf
       * @return true, kui kaari on sama palju ja samased (equals)
       */
      private boolean hasSameArcs(Graph g) {
          HashSet<Arc> thisGraphArcs = new HashSet<>();
          Vertex v = first;
          int n = 0;
          while (v != null) {
              Arc a = v.first;
              while (a != null) {
                  thisGraphArcs.add(a);
                  a = a.next;
              }
              v = v.next;
          }
          v = g.first;
          n = thisGraphArcs.size();
          // kontrollib, kas servad on samad
          while (v != null) {
              Arc a = v.first;
              while (a != null) {
                  if (thisGraphArcs.contains(a)) n--; else return false;
                  a = a.next;
              }
              v = v.next;
          }
          if (n > 0) return false; // kui jääb üle, siis ei ole võrdsed
          return true;
      }

      /** Tagasta graafi tippude ahela esimene tipp
       * @return ahela esimene tipp
       */
      public Vertex getFirst() {
         return first;
      }

      @Override
      protected Object clone() throws CloneNotSupportedException {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean equals(Object o) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int hashCode() {
         throw new UnsupportedOperationException();
      }


   }
}




